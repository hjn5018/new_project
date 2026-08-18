# 백엔드 커리어 성장을 위한 신규 프로젝트 구축 레포지토리 🚀

본 저장소는 백엔드 개발자 역량 강화 및 채용 공고(JD)의 주요 기술 요구사항을 반영하여, 용어 정리부터 소규모 기술 PoC, 프로젝트 후보군 도출, 그리고 최종 프로젝트 상세 설계까지의 전 과정을 기록하고 관리하는 프로젝트입니다.

---

## 📌 주요 목차 및 문서 바로가기

| 분류 | 주요 내용 | 문서/페이지 링크 |
| :--- | :--- | :--- |
| 📘 **용어 및 개념 정리** | 83+ 용어 정리 & **19대 주제별 개별 인터랙티브 웹 페이지** | [jargons.md](docs/jargons.md) / [jargons.html](docs/jargons.html) |
| 🚀 **19대 백엔드 세부 주제** | SPOF, MSA통신, NoSQL, VectorDB, Kafka CDC, DLQ, TDD 등 개별 페이지 | [jargons.md#19대-백엔드-세부-주제별-개별-웹-페이지-바로가기](docs/jargons.md#19대-백엔드-세부-주제별-개별-웹-페이지-바로가기) |
| 🧪 **사전 기술 검증 PoC 5종** | 동시성/대기열, 메시징이원화, 하이브리드DB, 결제/정산, 관제 5대 PoC | [project_candidates.md#사전-단계-용어-검증용-소규모-micro-project--poc-5종-심층-분석](docs/project_candidates.md#사전-단계-용어-검증용-소규모-micro-project--poc-5종-심층-분석) |
| 💡 **프로젝트 후보군 3종** | 실무급 프로젝트 후보 3종 및 UI 와이어프레임 | [project_candidates.md](docs/project_candidates.md) / [project_candidates.html](docs/project_candidates.html) |
| 🎯 **최종 선정 프로젝트** | NoSQL, VectorDB, Kafka, RabbitMQ, TDD, 결제, Redis 기반 프로젝트 상세 | [selected_project_details.md](docs/selected_project_details.md) |
| 🗺️ **실행 계획 & 로드맵** | 3-File System 기반 단계별 개발 로드맵 & GCP 배치안 | [selected_project_plan.md](docs/selected_project_plan.md) |

---

## 🧪 사전 검증 PoC 5종 및 프로젝트 후보 3종 문서 모음

### 🔬 사전 기술 검증 PoC 5종 (Micro-PoCs)
1. **PoC 1: Redis 동시성 & 대기열 제어** ➔ [상세 설계서 (details.md)](docs/pocs/poc1_redis_concurrency/poc1_details.md) | [실행 계획서 (plan.md)](docs/pocs/poc1_redis_concurrency/poc1_plan.md)
2. **PoC 2: Kafka vs RabbitMQ 메시징 이원화 & DLQ 격리** ➔ [상세 설계서 (details.md)](docs/pocs/poc2_messaging_dual/poc2_details.md) | [실행 계획서 (plan.md)](docs/pocs/poc2_messaging_dual/poc2_plan.md)
3. **PoC 3: NoSQL (MongoDB) & VectorDB (pgvector) 하이브리드** ➔ [상세 설계서 (details.md)](docs/pocs/poc3_nosql_vector/poc3_details.md) | [실행 계획서 (plan.md)](docs/pocs/poc3_nosql_vector/poc3_plan.md)
4. **PoC 4: PortOne 결제 Webhook 멱등성 & Spring Batch 정산** ➔ [상세 설계서 (details.md)](docs/pocs/poc4_payment_batch/poc4_details.md) | [실행 계획서 (plan.md)](docs/pocs/poc4_payment_batch/poc4_plan.md)
5. **PoC 5: Docker 컨테이너 & Observability 풀스택 관제** ➔ [상세 설계서 (details.md)](docs/pocs/poc5_docker_observability/poc5_details.md) | [실행 계획서 (plan.md)](docs/pocs/poc5_docker_observability/poc5_plan.md)

### 💡 실무급 프로젝트 후보 3종 (Candidates)
1. **후보 1: 대용량 선착순 결제/정산 & AI 트랜잭션 관제 플랫폼 [최종 추천]** ➔ [상세 설계서 (details.md)](docs/candidates/candidate1_flash_pay/candidate1_details.md) | [실행 계획서 (plan.md)](docs/candidates/candidate1_flash_pay/candidate1_plan.md)
2. **후보 2: EDA 기반 AI 멀티 에이전트 커머스 & 자동 정산 오케스트레이터** ➔ [상세 설계서 (details.md)](docs/candidates/candidate2_ai_agent_commerce/candidate2_details.md) | [실행 계획서 (plan.md)](docs/candidates/candidate2_ai_agent_commerce/candidate2_plan.md)
3. **후보 3: 실시간 금융 거래 관제 & 장애 격리 핀테크 플랫폼** ➔ [상세 설계서 (details.md)](docs/candidates/candidate3_fintech_realtime_control/candidate3_details.md) | [실행 계획서 (plan.md)](docs/candidates/candidate3_fintech_realtime_control/candidate3_plan.md)

---

## 🚀 19대 세부 주제별 개별 웹 페이지 바로가기

- [SPOF (단일 장애점)](docs/jargons/spof.html)
- [MSA 서비스 간 통신 (REST/gRPC/Kafka)](docs/jargons/msa-communication.html)
- [로드밸런싱 & 수평 확장 (Scale-Out)](docs/jargons/load-balancing.html)
- [EDA & MQTT 비교](docs/jargons/eda-mqtt.html)
- [Kafka CDC (Debezium)](docs/jargons/kafka-cdc.html)
- [DLQ 예외 처리 프로세스](docs/jargons/dlq.html)
- [NoSQL 4대 구조 비교](docs/jargons/nosql.html)
- [MongoDB 실무 활용 사례](docs/jargons/mongodb-usage.html)
- [VectorDB & RAG Visualizer](docs/jargons/vectordb.html)
- [Sharding-JDBC 라우팅](docs/jargons/sharding-jdbc.html)
- [JPA vs QueryDSL 나란히 비교](docs/jargons/jpa-querydsl.html)
- [PG vs VAN 결제 승인 다이어그램](docs/jargons/pg-van.html)
- [펌뱅킹 정산 이체 프로세스](docs/jargons/firmbanking.html)
- [ELK 스택 수집 파이프라인](docs/jargons/elk.html)
- [ELK vs OpenTelemetry vs LGTM](docs/jargons/observability.html)
- [REST API vs GraphQL vs gRPC](docs/jargons/api-protocols.html)
- [WebSocket vs STOMP 프레임](docs/jargons/websocket-stomp.html)
- [Node.js vs NestJS vs Next.js](docs/jargons/js-ecosystem.html)
- [TDD 사례 & 워크플로우](docs/jargons/tdd-cases.html)

---

## 🛠️ 필수 수록 기술 스택 (Must-Have Stack)

- **Database / Data Storage**: MySQL / PostgreSQL, **NoSQL (MongoDB)**, **VectorDB (pgvector / ChromaDB)**
- **Caching & In-Memory**: **Redis** (ZSET 대기열, Redisson 분산락, Pub/Sub)
- **Message Broker & Event Driven**: **Kafka** (대용량 스트리밍), **RabbitMQ** (작업 큐, DLQ)
- **Payment & Batch Processing**: **PortOne 결제/환불/Webhook**, Spring Batch (정산 엔진)
- **DevOps & Container**: **Docker / Docker Compose**, GCP Cloud Run / GKE
- **Quality & Methodology**: **TDD (Test-Driven Development)**, JUnit 5, Mockito
- **Observability & Monitoring**: Prometheus, Grafana, ELK Stack

---

## 📋 진행 상태 체크리스트

- [x] [pre_project_summary.md](docs/pre_project_summary.md) 요구사항 분석 및 GCP 자원 전략 수립
- [x] 83개 이상 용어 카테고리화 및 [jargons.md](docs/jargons.md) 작성
- [x] **19대 주제별 개별 인터랙티브 웹 페이지 (`docs/jargons/*.html`) 19종 구축 완료**
- [x] 대화형 용어 탐색기 [jargons.html](docs/jargons.html) 구축 및 19대 링크 연결
- [x] 사전 기술 검증 5대 소규모 micro-project / PoC 상세설계 및 실행계획 작성 완료
- [x] 3대 프로젝트 후보군 상세설계 및 실행계획, 인터랙티브 와이어프레임 [project_candidates.html](docs/project_candidates.html) 완성
- [x] [selected_project_details.md](docs/selected_project_details.md) 및 [selected_project_plan.md](docs/selected_project_plan.md) 최종 설계 완료
- [x] **문서 전체 `docs/` 폴더 체계화 및 레포지토리 구조 정돈 완료**
- [x] Git 버전 관리 및 GitHub 레포지토리 (`https://github.com/hjn5018/new_project`) 원격 동기화

---

## 🌐 GitHub Repository
- Remote URL: [https://github.com/hjn5018/new_project](https://github.com/hjn5018/new_project)
