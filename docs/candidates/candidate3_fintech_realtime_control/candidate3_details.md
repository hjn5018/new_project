# 💡 프로젝트 후보 3 상세 설계 명세서 (Candidate 3 Details)

## 📌 프로젝트 명칭
**"실시간 금융 거래 관제 & 장애 격리 핀테크 플랫폼 (Fintech Real-Time Control & FDS System)"**

---

## 💡 1. 프로젝트 개념 및 비즈니스 목표
- **개념**: 펌뱅킹(Firmbanking), 오픈뱅킹 이체, 대용량 간편결제 트랜잭션을 실시간 스트리밍(Kafka)으로 수집하고, VectorDB 패턴 매칭 및 룰 기반 **이상 금융 거래(FDS, Fraud Detection System)**를 밀리초 단위로 탐지하며, 외부 은행망 장애 발생 시 **서킷 브레이커와 DLQ**로 장애를 즉시 격리하는 엔터프라이즈급 핀테크 코어 플랫폼.
- **금융 특화 핵심 목표**:
  1. **초당 수만 건의 무손실 거래 로깅**: 금융 거래 내역에 대한 데이터 무손실(Zero Data Loss) 및 정확한 1회 처리(Exactly-Once Semantics) 보장.
  2. **실시간 이상 거래(FDS) 차단**: 비정상적인 IP, 단시간 거액 이체, 과거 사기 패턴 벡터 유사도 분석을 통한 자동 지급 정지.
  3. **장애 격리 및 자정 원장 대사**: 은행망 타임아웃 시 DLQ 격리 및 자정 Spring Batch를 통한 은행 원장과의 100% 잔액 일치 대사(Reconciliation).

---

## 🛠️ 2. 필수 기술스택 매핑 (Tech Stack Matrix)

| 구분 | 적용 기술 | 세부 역할 및 아키텍처 매핑 |
| :--- | :--- | :--- |
| **Transaction Core** | Java 17, Spring Boot 3.3, JPA, Resilience4j | 서킷 브레이커(Circuit Breaker) 기반 금융 거래 코어 서비스 |
| **Real-Time Stream** | Apache Kafka 3.7 (Streams / Partitions) | 펌뱅킹 이체/결제 트랜잭션 초고속 스트리밍 |
| **Task Queue & DLQ**| RabbitMQ 3.13 (AMQP DLX) | 실패 거래 격리, 재시도 큐, 수동 원장 조정 큐 |
| **Multi-Database** | PostgreSQL 16 (원장 RDB), MongoDB (가변 전문 로그), pgvector (FDS 패턴 임베딩) | 금융 원장 무결성, 전문 원본 BSON, 사기 거래 패턴 벡터 |
| **Cache & Lock** | Redis 7.2 (Redisson 분산 락, 계좌 잔액 캐싱) | 계좌 출금 시 동시성 분산 락 및 멱등 이체 토큰 |
| **Reconciliation Batch**| Spring Batch 5.1 | 자정 은행 전문 vs 플랫폼 원장 100% 불일치 대사 작업 |
| **Observability** | OpenTelemetry, Prometheus, Grafana | 계좌 이체 지연시간 SLA(99.9% < 50ms) 및 FDS 탐지율 관제 |

---

## 🏗️ 3. 전체 시스템 아키텍처 다이어그램 (Architecture)

```mermaid
flowchart TB
    subgraph ClientAndBanks ["Client & 외부 은행 펌뱅킹 망"]
        UserApp[핀테크 앱 / 사용자]
        BankCore[시중 은행 펌뱅킹 코어망]
    end

    subgraph CoreFintechTier ["핀테크 금융 코어 서버 (Spring Boot)"]
        TransferService[계좌 이체 & 펌뱅킹 서비스]
        CircuitBreaker[Resilience4j 서킷 브레이커]
        FdsEngine[실시간 FDS 탐지 엔진]
        ReconBatch[자정 원장 대사 엔진 (Spring Batch)]
    end

    subgraph MessagingTier ["Streaming & Isolation Broker"]
        KafkaStream["Kafka (transactions.stream / 6 Partitions)"]
        RabbitDLQ["RabbitMQ (failed-trans.dlq & 재처리 파이프라인)"]
    end

    subgraph StorageTier ["Multi-Database Layer"]
        AccountLedger[("PostgreSQL 16\n(계좌 원장, 거래 내역)")]
        FraudVector[("PostgreSQL pgvector\n(FDS 사기 패턴 벡터)")]
        RawLogsMongo[("MongoDB\n(은행 전문 ISO-8583 원본 BSON)")]
        RedisCache[("Redis 7.2\n(계좌 분산락 & 멱등성 토큰)")]
    end

    UserApp --> TransferService
    TransferService --> RedisCache
    TransferService --> CircuitBreaker
    CircuitBreaker --> BankCore
    
    TransferService --> KafkaStream
    KafkaStream --> FdsEngine
    
    FdsEngine --> FraudVector
    FdsEngine --> AccountLedger
    
    CircuitBreaker -.->|은행 타임아웃 발생 시| RabbitDLQ
    RabbitDLQ -.->|관리자 원장 수기 조정| AccountLedger
    
    TransferService --> RawLogsMongo
    BankCore -.->|자정 은행 대사 파일| ReconBatch
    ReconBatch --> AccountLedger
```

---

## 💾 4. 핵심 데이터 모델 및 스키마

### A. 금융 계좌 및 원장 테이블 (PostgreSQL DDL)
```sql
CREATE TABLE accounts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_number VARCHAR(30) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    balance DECIMAL(15,2) NOT NULL CHECK (balance >= 0),
    status VARCHAR(20) NOT NULL, -- ACTIVE, FROZEN, CLOSED
    version BIGINT DEFAULT 0
);

CREATE TABLE transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tx_id VARCHAR(64) NOT NULL UNIQUE,
    from_account_id BIGINT NOT NULL,
    to_account_id BIGINT NOT NULL,
    amount DECIMAL(15,2) NOT NULL,
    tx_type VARCHAR(20) NOT NULL, -- TRANSFER, WITHDRAW, DEPOSIT
    status VARCHAR(20) NOT NULL,   -- SUCCESS, PENDING, FAILED, SUSPECTED_FRAUD
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### B. FDS 사기 거래 패턴 벡터 (pgvector)
```sql
CREATE TABLE fraud_patterns (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    pattern_type VARCHAR(50) NOT NULL,
    risk_score INT NOT NULL,
    feature_vector vector(128) NOT NULL -- 거래시간, 금액, 디바이스, 위치 기반 특징 벡터
);
```

---

## ⚡ 5. 차별화 핵심 기술 포인트

1. **금융 원장 트랜잭션 무결성 (ACID + 분산 락)**:
   - 계좌 잔액 차감 시 Redisson 락과 낙관적 락(Optimistic Lock)을 이중 적용하여 동시 다발적 출금 시 마이너스 잔액 발생 원천 차단.
2. **Resilience4j 기반 서킷 브레이커 & DLQ 격리**:
   - 특정 은행망 장애율 50% 초과 시 서킷 Open -> 즉시 사용자에게 '점검 중' 안내 반환 및 실패 건 DLQ 보관으로 타 거래 영향 격리.
3. **자정 원장 불일치 100% 대사 (Reconciliation Batch)**:
   - 은행 전문 CSV와 플랫폼 DB 거래 내역을 1:1 비교하여 1원 단위 오차까지 자동 검출 및 보정 트랜잭션 실행.
