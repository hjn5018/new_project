# 🧪 PoC 5 상세 설계 명세서: Docker 컨테이너 & Observability 풀스택 관제 (Container & Observability PoC Details)

## 📌 PoC 개요
- **PoC 명칭**: Docker Compose 기반 5대 미들웨어 통합 환경 구축 및 OpenTelemetry / ELK 풀스택 관제 PoC
- **주요 목표**:
  1. **단일 명령 인프라 가상화**: `docker compose up -d` 한 줄로 Spring Boot App, MySQL, Redis, Kafka, MongoDB 등 분산 서비스 전체를 무결점 기동.
  2. **풀스택 Observability (M·L·T)**: OpenTelemetry Java Agent를 통한 W3C Trace ID 전 구간 분산 추적, Logback+Logstash 구조화 로그 수집, Prometheus+Grafana 메트릭 대시보드 구축.

---

## 🛠️ 1. 핵심 기술스택 매핑 (Tech Stack)

| 기술 요소 | 버전 / 도구 | 선정 이유 및 역할 |
| :--- | :--- | :--- |
| **Container Engine**| Docker 26.x & Docker Compose v2 | 컨테이너 네트워크 브릿지 격리 및 원클릭 인프라 프로비저닝 |
| **Distributed Trace**| OpenTelemetry Java Agent 2.x | 코드 수정 없는 Bytecode 인스트루멘테이션 & W3C Trace ID 주입 |
| **Trace Collector** | OpenTelemetry Collector / Jaeger | APM 스팬 데이터 집계 및 분산 트레이스 시각화 |
| **Logging Pipeline** | ELK Stack (Elasticsearch, Logstash, Kibana) | JSON 구조화 로그 수집 및 실시간 에러 검색 |
| **Metrics & Monitor**| Prometheus & Grafana | JVM CPU/Memory, Redis 메모리, Kafka 랙 실시간 대시보드 |

---

## 🏗️ 2. 아키텍처 다이어그램 (Observability Pipeline)

```mermaid
flowchart TB
    subgraph MultiContainerEnv ["Docker Compose 통합 가상화 환경"]
        App["Spring Boot Application\n(OTel Java Agent Attached)"]
        MySQL["MySQL 8.0"]
        Redis["Redis 7.2"]
        Kafka["Apache Kafka"]
        Mongo["MongoDB 7.0"]
    end

    subgraph ObservabilityLayer ["풀스택 관제 인프라 (LGTM & ELK)"]
        OTelCollector["OpenTelemetry Collector"]
        Jaeger["Jaeger / Tempo\n(Distributed Tracing)"]
        Logstash["Logstash (TCP 5000)"]
        Elasticsearch["Elasticsearch 8.13"]
        Kibana["Kibana Dashboard"]
        Prometheus["Prometheus (Scraper)"]
        Grafana["Grafana (Metrics Dashboard)"]
    end

    App -->|W3C Trace Context (HTTP/gRPC/Kafka)| OTelCollector
    OTelCollector --> Jaeger
    
    App -->|Logstash Logback Appender (JSON)| Logstash
    Logstash --> Elasticsearch
    Elasticsearch --> Kibana
    
    App -->|/actuator/prometheus| Prometheus
    Prometheus --> Grafana

    App -.-> MySQL
    App -.-> Redis
    App -.-> Kafka
    App -.-> Mongo
```

---

## 💾 3. Docker Compose 및 로그 설정 (Configuration)

### A. Docker Compose 설정 발췌 (`docker-compose-poc5.yml`)
```yaml
version: '3.8'
services:
  app:
    build: .
    ports:
      - "8080:8080"
    environment:
      - JAVA_TOOL_OPTIONS=-javaagent:/opentelemetry-javaagent.jar
      - OTEL_SERVICE_NAME=poc5-core-app
      - OTEL_EXPORTER_OTLP_ENDPOINT=http://otel-collector:4317
      - SPRING_PROFILES_ACTIVE=docker
    depends_on:
      - mysql
      - redis
      - kafka
      - otel-collector

  otel-collector:
    image: otel/opentelemetry-collector:0.98.0
    ports:
      - "4317:4317" # OTLP gRPC
      - "4318:4318" # OTLP HTTP
    volumes:
      - ./otel-config.yaml:/etc/otel-collector-config.yaml
```

### B. Logback 구조화 로그 설정 (`logback-spring.xml`)
```xml
<configuration>
    <appender name="LOGSTASH" class="net.logstash.logback.appender.LogstashTcpSocketAppender">
        <destination>logstash:5000</destination>
        <encoder class="net.logstash.logback.encoder.LogstashEncoder">
            <includeContext>true</includeContext>
            <customFields>{"service":"poc5-app"}</customFields>
        </encoder>
    </appender>
    <root level="INFO">
        <appender-ref ref="LOGSTASH" />
    </root>
</configuration>
```

---

## ⚡ 4. 핵심 관제 추적 흐름 (Tracing Workflow)

1. **HTTP 요청 수신**: `POST /api/v1/orders` 인입 시 OTel Agent가 HTTP Header에 `traceparent` (Trace ID: `4bf92f3577b34da6a3ce929d0e0e4736`) 자동 주입.
2. **미들웨어 연동**:
   - Redis 명령어(`ZADD`, `RLock`) 실행 시 Span 생성.
   - Kafka 메시지 발행 시 Kafka Record Header에 `traceparent` 전파.
   - MySQL 쿼리 실행 시 DB Span 생성.
3. **결과 시각화**: Jaeger UI에서 단일 API 호출의 [API -> Redis -> MySQL -> Kafka] 전 구간 지연시간(Waterfall View) 및 병목 지점 실시간 확인.

---

## 🧪 5. 관제 검증 전략

- **분산 트레이싱 전파 검증**:
  - 주문 생성 API 호출 후 Jaeger 웹 UI(`http://localhost:16686`)에서 해당 요청의 Trace ID를 검색하여 HTTP Controller부터 Kafka Consumer까지 모든 하위 Span이 연결되어 표시되는지 확인.
- **ELK 로그 수집 및 검색 검증**:
  - 인위적으로 500 에러 발생 후 Kibana Discovery에서 `level: "ERROR"` 필터링 시 에러 스택 트레이스가 JSON 구조화 형태로 1초 내에 인덱싱되는지 확인.
