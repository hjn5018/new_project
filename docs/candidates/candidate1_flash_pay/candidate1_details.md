# 🏆 프로젝트 후보 1 상세 설계 명세서 (Candidate 1 Details)

## 📌 프로젝트 명칭
**"대용량 선착순 결제·정산 & AI 트랜잭션 관제 플랫폼 (Flash-Pay & AI Analytics Platform)"**

---

## 💡 1. 프로젝트 개념 및 비즈니스 목표
- **개념**: 초당 수천 건의 대규모 트래픽이 몰리는 한정판 티켓/상품 선착순 구매 이벤트를 Redis 대기열 및 Redisson 분산 락으로 안정적으로 처리하고, Kafka와 RabbitMQ를 이원화하여 결제/정산/알림 파이프라인을 비동기로 제어하며, pgvector와 AI Agent를 결합하여 사용자 맞춤 상담 및 실시간 거래 관제를 제공하는 엔터프라이즈급 백엔드 플랫폼.
- **사전 PoC 5종과의 100% 통합성**:
  - **PoC 1 (대기열/분산락)**: 선착순 진입 순번 제어 및 동시성 100% 보장.
  - **PoC 2 (Kafka/RabbitMQ)**: 대용량 주문 이벤트 스트리밍 + 알림 큐 및 DLQ 장애 격리.
  - **PoC 3 (MongoDB/pgvector)**: 비정형 결제 로그 BSON 적재 + RAG 기반 AI 고객 상담.
  - **PoC 4 (PortOne/Spring Batch)**: 결제 Webhook 멱등성 보장 + 자정 대용량 청크 정산.
  - **PoC 5 (Docker/OTel 관제)**: 멀티 컨테이너 환경 + OpenTelemetry 분산 트레이싱 및 대시보드.

---

## 🛠️ 2. 필수 기술스택 매핑 (Tech Stack Matrix)

| 구분 | 적용 기술 | 세부 역할 및 아키텍처 매핑 |
| :--- | :--- | :--- |
| **Backend Core** | Java 17, Spring Boot 3.3, JPA, QueryDSL | 도메인 비즈니스 로직, 고성능 REST API |
| **In-Memory & Lock** | Redis 7.2, Redisson | ZSET 대기열, SETNX 멱등성 락, RLock 분산 락 |
| **Dual Message Broker**| Apache Kafka 3.7 & RabbitMQ 3.13 | 대용량 트랜잭션 스트리밍(Kafka) & 비동기 알림/DLQ(RabbitMQ) |
| **Multi-Database** | PostgreSQL 16 (RDB), MongoDB 7 (NoSQL), pgvector (VectorDB) | 주문/결제 ACID, 비정형 로그/대화 세션, AI 텍스트 임베딩 |
| **Batch & Payment** | Spring Batch 5.1, PortOne V2 API | 1,000건 Chunk 대용량 정산, PG 결제/환불/웹훅 |
| **AI & RAG** | Spring AI, OpenAI text-embedding-3-small | FAQ 유사도 검색 및 자동 상담 에이전트 |
| **DevOps & 관제** | Docker Compose, GCP Cloud Run, OTel, Prometheus, Grafana | 컨테이너 가상화, 클라우드 배포, M·L·T 풀스택 관제 |

---

## 🏗️ 3. 전체 시스템 아키텍처 다이어그램 (Architecture)

```mermaid
flowchart TB
    subgraph ClientTier ["Client & Ingress Layer"]
        User[웹/모바일 클라이언트 (대량 사용자)]
        NginxGateway[Nginx API Gateway / GCP Cloud Run]
    end

    subgraph MemoryTier ["Redis In-Memory Tier"]
        WaitingZSet["Redis ZSET 대기열\n(Score: Timestamp 순번 보장)"]
        ActiveTokens["Redis Active Set\n(인입 허용 토큰 관리)"]
        RedissonLock["Redisson Distributed Lock\n(상품별 재고 분산 락)"]
    end

    subgraph BackendTier ["Spring Boot Core Microservices"]
        OrderService[주문 & 티켓팅 서비스]
        PaymentService[PortOne 결제 & Webhook 서비스]
        SettlementBatch[Spring Batch 대용량 정산 엔진]
        AIAgentService[AI RAG 고객 상담 서비스]
    end

    subgraph MessagingTier ["Dual Event Streaming Infrastructure"]
        KafkaCluster["Apache Kafka\nTopic: order.events (대용량 스트림)"]
        RabbitMQCluster["RabbitMQ & DLQ\nExchange: noti.direct (작업 큐 & 격리)"]
    end

    subgraph StorageTier ["Multi-Database Layer"]
        PostgresRDB[("PostgreSQL 16\n(Users, Products, Orders, Payments)")]
        PgVectorDB[("PostgreSQL pgvector\n(Product & FAQ Embeddings)")]
        MongoNoSQL[("MongoDB 7.0\n(Webhook Raw Logs, Chat Sessions)")]
    end

    User --> NginxGateway
    NginxGateway --> WaitingZSet
    WaitingZSet --> ActiveTokens
    ActiveTokens --> OrderService
    
    OrderService --> RedissonLock
    RedissonLock --> PostgresRDB
    
    OrderService --> KafkaCluster
    PaymentService --> RabbitMQCluster
    
    KafkaCluster --> SettlementBatch
    RabbitMQCluster --> MongoNoSQL
    
    PaymentService --> PostgresRDB
    PaymentService --> MongoNoSQL
    AIAgentService --> PgVectorDB
    AIAgentService --> MongoNoSQL
```

---

## 💾 4. 핵심 데이터 모델 및 ERD (Data Schema)

### A. RDBMS Schema (PostgreSQL)
- `users`: `id (PK)`, `email`, `name`, `role`, `created_at`
- `products`: `id (PK)`, `name`, `price`, `stock`, `version`, `created_at`
- `orders`: `id (PK)`, `user_id (FK)`, `product_id (FK)`, `quantity`, `total_price`, `status`, `created_at`
- `payments`: `id (PK)`, `order_id (FK)`, `imp_uid (UK)`, `merchant_uid`, `amount`, `status`, `paid_at`
- `settlements`: `id (PK)`, `seller_id`, `total_sales`, `fee`, `settlement_amount`, `settlement_date`, `status`

### B. NoSQL Schema (MongoDB)
- `payment_webhook_logs`: `{ _id, impUid, merchantUid, rawPayload: Object, receivedAt }`
- `ai_chat_sessions`: `{ _id, userId, turns: [{ role, message, timestamp }], metadata }`

### C. VectorDB Schema (pgvector)
- `faq_embeddings`: `id (PK)`, `category`, `question`, `answer`, `embedding (vector(1536))`

---

## ⚡ 5. 차별화 핵심 기술 포인트

1. **초고속 선착순 대기열 및 무결점 재고 차감**:
   - Redis ZSET을 통해 진입 트래픽을 초당 50~100건 단위로 Throttling하고, Redisson 분산락을 통해 1,000 TPS 동시 인입 상황에서도 마이너스 재고 0건 달성.
2. **이벤트 스트리밍 및 장애 격리(Fault-Tolerance)**:
   - Kafka를 통한 고속 정산/통계 파이프라인 연계 및 RabbitMQ DLX를 통한 외부 통신 장애 무격리 보장.
3. **RAG 기반 지능형 CS & 실시간 관제**:
   - pgvector 유사도 검색을 통한 FAQ 자동 응대 및 OpenTelemetry 기반 E2E 트레이싱.
