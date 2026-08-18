# 💡 프로젝트 후보 2 상세 설계 명세서 (Candidate 2 Details)

## 📌 프로젝트 명칭
**"EDA 기반 AI 멀티 에이전트 커머스 & 자동 정산 오케스트레이터 (AI Agent Commerce & Settlement Platform)"**

---

## 💡 1. 프로젝트 개념 및 비즈니스 목표
- **개념**: 사용자와의 자연어 대화(Natural Language)만으로 상품 탐색, 재고 확인, 선착순 결제, 주문 변경, 실시간 배송/정산 추적까지 수행하는 **AI 멀티 에이전트 기반 차세대 지능형 이커머스 백엔드 플랫폼**.
- **멀티 에이전트 협업 구조 (LangGraph / Function Calling)**:
  - **Supervisor Agent**: 사용자 의도 파악 및 전문 하위 에이전트로 작업 라우팅.
  - **Product Recommender Agent**: pgvector 시맨틱 검색을 활용한 개인화 상품 추천.
  - **Order & Payment Agent**: Redis 분산락 및 PortOne API 연동을 통한 안전한 결제 처리.
  - **Settlement Agent**: Spring Batch 및 Kafka 이벤트를 통한 판매자 자동 정산.

---

## 🛠️ 2. 필수 기술스택 매핑 (Tech Stack Matrix)

| 구분 | 적용 기술 | 세부 역할 및 아키텍처 매핑 |
| :--- | :--- | :--- |
| **Agent Orchestration**| Spring AI / LangGraph, OpenAI GPT-4o | Tool/Function Calling 기반 멀티 에이전트 상태 머신 제어 |
| **Task Queue & EDA** | RabbitMQ (에이전트 태스크 분배), Kafka (행동 이벤트 로그) | 에이전트 간 비동기 작업 큐 및 분석용 스트리밍 |
| **Multi-Database** | PostgreSQL 16 (RDB), MongoDB (대화 세션), pgvector (상품 임베딩) | 트랜잭션, 멀티턴 대화 기록, 1536차원 상품 시맨틱 검색 |
| **Caching & Locking** | Redis (대화 컨텍스트 캐시, Redisson 분산락) | 에이전트 대화 캐싱 및 선착순 상품 주문 동시성 제어 |
| **Payment & Batch** | PortOne API, Spring Batch 5 | 자연어 명령 기반 결제 승인 및 판매자 수수료 정산 |
| **DevOps & 관제** | Docker Compose, Prometheus, Grafana, OpenTelemetry | 분산 에이전트 호출 지연시간 및 토큰 사용량 관제 |

---

## 🏗️ 3. 전체 시스템 아키텍처 다이어그램 (Architecture)

```mermaid
flowchart TB
    subgraph UserLayer ["User Interface"]
        User[사용자 자연어 입력: '캠핑용 경량 텐트 추천해줘서 결제까지 해줘']
    end

    subgraph AgentOrchestrator ["AI Multi-Agent Core (LangGraph / Spring AI)"]
        Supervisor[Supervisor Agent (의도 분류 & 오케스트레이터)]
        
        subgraph SubAgents ["전문 도메인 서브 에이전트"]
            RecAgent[추천 에이전트\n(Product Recommender)]
            PayAgent[주문/결제 에이전트\n(Order & Payment)]
            CSAgent[상담/취소 에이전트\n(Support & Refund)]
        end
        
        Supervisor --> RecAgent
        Supervisor --> PayAgent
        Supervisor --> CSAgent
    end

    subgraph AsyncBroker ["EDA & Task Queues"]
        RabbitMQ["RabbitMQ (에이전트 비동기 작업 큐 & DLQ)"]
        KafkaBroker["Kafka (사용자 대화/행동 로그 스트림)"]
    end

    subgraph DataTier ["Data & Storage Layer"]
        MongoChat[("MongoDB\n(Multi-turn Chat Session)")]
        PgVector[("PostgreSQL pgvector\n(Product Embeddings & Specs)")]
        RDB[("PostgreSQL RDBMS\n(Orders, Payments, Settlements)")]
        RedisLock[("Redis & Redisson\n(Stock Lock & State Cache)")]
    end

    User --> Supervisor
    Supervisor --> MongoChat
    
    RecAgent --> PgVector
    PayAgent --> RedisLock
    PayAgent --> RDB
    
    Supervisor --> RabbitMQ
    RabbitMQ --> KafkaBroker
    KafkaBroker --> RDB
```

---

## 💾 4. 핵심 데이터 모델 및 스키마

### A. AI 대화 및 에이전트 상태 (MongoDB)
```json
{
  "_id": "agent_session_881",
  "userId": 1002,
  "currentState": "AWAITING_PAYMENT_CONFIRMATION",
  "contextData": {
    "recommendedProductId": 204,
    "productName": "초경량 알파인 텐트 2인용",
    "price": 189000
  },
  "history": [
    { "sender": "USER", "text": "가벼운 텐트 추천해줘", "timestamp": 1787123000 },
    { "sender": "AGENT_REC", "text": "초경량 알파인 텐트를 추천합니다.", "timestamp": 1787123002 }
  ]
}
```

### B. 상품 벡터 임베딩 (PostgreSQL pgvector)
```sql
CREATE TABLE product_embeddings (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    product_id BIGINT NOT NULL,
    category VARCHAR(50),
    spec_summary TEXT NOT NULL,
    embedding vector(1536) NOT NULL
);
```

---

## ⚡ 5. 차별화 핵심 기술 포인트

1. **자연어 기반 엔드투엔드 트랜잭션 완결성**:
   - 단순 챗봇을 넘어 Tool Calling을 통해 백엔드 DB/PG사 결제 API를 직접 호출하여 실제 트랜잭션(재고 차감, 결제 승인)을 생성.
2. **에이전트 태스크 비동기 디커플링 (RabbitMQ)**:
   - 외부 LLM 지연시간 및 타임아웃 발생 시 RabbitMQ 작업 큐를 통해 백그라운드에서 안전하게 상태 복구.
3. **지능형 시맨틱 상품 검색 (pgvector)**:
   - "여름에 시원한 통풍 잘되는 캠핑 의자"와 같은 복합적인 자연어 질의에 대해 최적의 상품을 1536차원 벡터 공간에서 실시간 추천.
