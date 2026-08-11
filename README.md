# 백엔드 커리어 성장을 위한 신규 프로젝트 구축 레포지토리 🚀

본 저장소는 백엔드 개발자 역량 강화 및 채용 공고(JD)의 주요 기술 요구사항을 반영하여, 용어 정리부터 소규모 기술 PoC, 프로젝트 후보군 도출, 그리고 최종 프로젝트 상세 설계까지의 전 과정을 기록하고 관리하는 프로젝트입니다.

---

## 📌 주요 목차 및 문서 바로가기

| 분류 | 주요 내용 | 문서/페이지 링크 |
| :--- | :--- | :--- |
| 📘 **용어 및 개념 정리** | 83+ 용어 정리 & **19대 주제별 개별 인터랙티브 웹 페이지** | [jargons.md](jargons.md) / [jargons.html](jargons.html) |
| 🚀 **19대 백엔드 세부 주제** | SPOF, MSA통신, NoSQL, VectorDB, Kafka CDC, DLQ, TDD 등 개별 페이지 | [jargons.md#19대-백엔드-세부-주제별-개별-웹-페이지-바로가기](jargons.md#19대-백엔드-세부-주제별-개별-웹-페이지-바로가기) |
| 🧪 **용어 실습 PoC** | 주요 기술 개별/그룹 단위 테스트 소규모 프로젝트 | [jargons.md#사전-단계-용어-검증용-소규모-micro-project--poc-5종](project_candidates.md#사전-단계-용어-검증용-소규모-micro-project--poc-5종) |
| 💡 **프로젝트 후보군** | 실무급 프로젝트 후보 3종 및 UI 와이어프레임 | [project_candidates.md](project_candidates.md) / [project_candidates.html](project_candidates.html) |
| 🎯 **최종 선정 프로젝트** | NoSQL, VectorDB, Kafka, RabbitMQ, TDD, 결제, Redis 기반 프로젝트 상세 | [selected_project_details.md](selected_project_details.md) |
| 🗺️ **실행 계획 & 로드맵** | 3-File System 기반 단계별 개발 로드맵 & GCP 배치안 | [selected_project_plan.md](selected_project_plan.md) |

---

## 🚀 19대 세부 주제별 개별 웹 페이지 바로가기

- [SPOF (단일 장애점)](jargons/spof.html)
- [MSA 서비스 간 통신 (REST/gRPC/Kafka)](jargons/msa-communication.html)
- [로드밸런싱 & 수평 확장 (Scale-Out)](jargons/load-balancing.html)
- [EDA & MQTT 비교](jargons/eda-mqtt.html)
- [Kafka CDC (Debezium)](jargons/kafka-cdc.html)
- [DLQ 예외 처리 프로세스](jargons/dlq.html)
- [NoSQL 4대 구조 비교](jargons/nosql-mongodb.html)
- [MongoDB 실무 활용 사례](jargons/mongodb-usage.html)
- [VectorDB & RAG Visualizer](jargons/vectordb.html)
- [Sharding-JDBC 라우팅](jargons/sharding-jdbc.html)
- [JPA vs QueryDSL 나란히 비교](jargons/jpa-querydsl.html)
- [PG vs VAN 결제 승인 다이어그램](jargons/pg-van.html)
- [펌뱅킹 정산 이체 프로세스](jargons/firmbanking.html)
- [ELK 스택 수집 파이프라인](jargons/elk.html)
- [ELK vs OpenTelemetry vs LGTM](jargons/observability.html)
- [REST API vs GraphQL vs gRPC](jargons/api-protocols.html)
- [WebSocket vs STOMP 프레임](jargons/websocket-stomp.html)
- [Node.js vs NestJS vs Next.js](jargons/js-ecosystem.html)
- [TDD 사례 & 워크플로우](jargons/tdd-cases.html)

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

- [x] [pre_project_summary.md](pre_project_summary.md) 요구사항 분석 및 GCP 자원 전략 수립
- [x] 83개 이상 용어 카테고리화 및 [jargons.md](jargons.md) 작성
- [x] **19대 주제별 개별 인터랙티브 웹 페이지 (`jargons/*.html`) 19종 구축 완료**
- [x] 대화형 용어 탐색기 [jargons.html](jargons.html) 구축 및 19대 링크 연결
- [x] 용어 검증용 5대 소규모 micro-project / PoC 가이드 정리
- [x] 3대 프로젝트 후보군 및 인터랙티브 와이어프레임 [project_candidates.html](project_candidates.html) 완성
- [x] [selected_project_details.md](selected_project_details.md) 및 [selected_project_plan.md](selected_project_plan.md) 최종 설계 완료
- [x] Git 버전 관리 및 GitHub 레포지토리 (`https://github.com/hjn5018/new_project`) 원격 동기화

---

## 🌐 GitHub Repository
- Remote URL: [https://github.com/hjn5018/new_project](https://github.com/hjn5018/new_project)
