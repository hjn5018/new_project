# 🧪 PoC 2 상세 설계 명세서: Kafka vs RabbitMQ 메시징 이원화 & DLQ 격리 (Messaging Dual & DLQ PoC Details)

## 📌 PoC 개요
- **PoC 명칭**: Apache Kafka 대용량 이벤트 스트리밍 및 RabbitMQ AMQP 라우팅 + DLQ(Dead Letter Queue) 장애 격리 PoC
- **주요 목표**:
  1. **Kafka**: 대용량 주문/결제 성공 이벤트를 실시간 분산 스트리밍으로 수집하여 파티션 기반 분산 처리 및 고속 처리율(High Throughput) 달성.
  2. **RabbitMQ**: 신뢰성 기반의 비동기 작업 큐(알림톡/SMS 발송)를 처리하고, 외부 장애 발생 시 3회 재시도 후 DLQ로 격리하여 시스템 무장애 유지.

---

## 🛠️ 1. 핵심 기술스택 매핑 (Tech Stack)

| 기술 요소 | 버전 / 도구 | 선정 이유 및 역할 |
| :--- | :--- | :--- |
| **Streaming Broker**| Apache Kafka 3.7 (Kraft / Zookeeper) | 초당 수만 건의 대용량 이벤트 로그 수집 및 순서 보장 파티셔닝 |
| **Task Queue** | RabbitMQ 3.13 (Management) | AMQP 기반 다양한 Exchange 라우팅(Direct/Topic) 및 DLX 기반 오류 격리 |
| **Framework** | Spring Boot 3.3.x, Spring Kafka, Spring AMQP | 메시지 발행/소비 어노테이션(`@KafkaListener`, `@RabbitListener`) 기반 비동기 파이프라인 |
| **Testing** | EmbeddedKafka, Testcontainers | 단위/통합 테스트 환경에서 브로커 실시간 구동 및 실패 재시도 검증 |

---

## 🏗️ 2. 아키텍처 다이어그램 (Dual Messaging Architecture)

```mermaid
flowchart TD
    OrderController[주문/결제 컨트롤러 (API)] -->|1. 주문 완료 이벤트 발행| KafkaProducer[Kafka Producer: OrderEventProducer]
    OrderController -->|2. 알림 발송 작업 발행| RabbitProducer[RabbitMQ Producer: NotificationProducer]

    subgraph KafkaCluster ["Apache Kafka (대용량 스트리밍)"]
        KafkaTopic["Topic: orders.completed (3 Partitions)"]
        ConsumerGroup["Consumer Group: analytics-service"]
        ConsumerAudit["Consumer Group: audit-service"]
        
        KafkaProducer --> KafkaTopic
        KafkaTopic --> ConsumerGroup
        KafkaTopic --> ConsumerAudit
    end

    subgraph RabbitMQCluster ["RabbitMQ & DLQ 장애 격리 파이프라인"]
        Exchange["Direct Exchange: noti.direct"]
        MainQueue["Queue: noti.sms.queue"]
        DLX["Dead Letter Exchange: noti.dlx"]
        DLQ["Dead Letter Queue: noti.sms.dlq"]

        RabbitProducer -->|Routing Key: noti.sms| Exchange
        Exchange --> MainQueue
        MainQueue -->|1차 알림 발송 시도| Worker[알림 발송 Worker]
        
        Worker -->|외부 통신 실패 ➔ 3회 Exponential Backoff 실패| DLX
        DLX --> DLQ
        
        AdminDashboard[운영자 콘솔 / 배포 스크립트] -->|수동 Redrive 메시지 복구| DLQ
        DLQ -.->|정상 복구 후 재처리| MainQueue
    end
```

---

## 💾 3. 메시지 페이로드 및 토픽/큐 설계

### A. Kafka Event Payload (JSON)
```json
{
  "eventId": "evt_20260819_001",
  "eventType": "ORDER_COMPLETED",
  "orderId": 1001,
  "userId": 501,
  "totalAmount": 45000,
  "timestamp": 1787123456789
}
```

### B. RabbitMQ Configuration
- **Exchange**: `noti.exchange` (Type: Direct, Durable: true)
- **Main Queue**: `noti.sms.queue`
  - `x-dead-letter-exchange`: `noti.dlx`
  - `x-dead-letter-routing-key`: `noti.sms.dead`
  - `x-message-ttl`: 60000 (1분)
- **DLQ**: `noti.sms.dlq` (Durable: true)

---

## ⚡ 4. 핵심 구현 코드 스니펫 (Core Code Snippets)

### 1. RabbitMQ DLQ 설정 빈 (Spring AMQP Config)
```java
@Configuration
public class RabbitConfig {
    public static final String MAIN_QUEUE = "noti.sms.queue";
    public static final String DLQ_QUEUE = "noti.sms.dlq";
    public static final String MAIN_EXCHANGE = "noti.exchange";
    public static final String DLX_EXCHANGE = "noti.dlx";

    @Bean
    public DirectExchange mainExchange() {
        return new DirectExchange(MAIN_EXCHANGE);
    }

    @Bean
    public DirectExchange dlxExchange() {
        return new DirectExchange(DLX_EXCHANGE);
    }

    @Bean
    public Queue mainQueue() {
        return QueueBuilder.durable(MAIN_QUEUE)
                .withArgument("x-dead-letter-exchange", DLX_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", "noti.sms.dead")
                .build();
    }

    @Bean
    public Queue dlqQueue() {
        return QueueBuilder.durable(DLQ_QUEUE).build();
    }

    @Bean
    public Binding mainBinding() {
        return BindingBuilder.bind(mainQueue()).to(mainExchange()).with("noti.sms");
    }

    @Bean
    public Binding dlqBinding() {
        return BindingBuilder.bind(dlqQueue()).to(dlxExchange()).with("noti.sms.dead");
    }
}
```

### 2. RabbitMQ Consumer 실패 재시도 및 DLQ 격리
```java
@Component
@Slf4j
public class NotificationConsumer {

    @RabbitListener(queues = RabbitConfig.MAIN_QUEUE)
    @Retryable(value = {ExternalApiException.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000, multiplier = 2.0))
    public void handleNotification(NotificationMessage message) {
        log.info("알림 발송 수신: {}", message);
        if (message.isSimulateFailure()) {
            throw new ExternalApiException("외부 SMS API 응답 없음 (장애 시뮬레이션)");
        }
        log.info("알림 발송 성공: {}", message.getUserId());
    }

    @Recover
    public void recover(ExternalApiException e, NotificationMessage message) {
        log.error("3회 재시도 실패. 메시지가 DLQ로 라우팅됩니다: {}", message.getUserId());
        throw new AmqpRejectAndDontRequeueException("Reject and send to DLQ", e);
    }
}
```

---

## 🧪 5. TDD 및 메시징 격리 검증 전략

- **Kafka 처리율 검증**:
  - `KafkaTemplate`으로 10,000건의 이벤트를 비동기 전송하여 Consumer Group이 순서 누락 없이 전부 수신 완료하는 시간을 측정 (Throughput > 3,000 msg/sec).
- **RabbitMQ DLQ 격리 검증**:
  - `simulateFailure=true` 메시지 발행 후, 3회 재시도 로그 확인 -> Main Queue에서 메시지가 제거되고 DLQ(`noti.sms.dlq`)에 정확히 1건 적재되었는지 `@Testcontainers` 기반 테스트로 증명.
