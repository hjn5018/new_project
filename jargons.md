# 백엔드 핵심 용어집 및 19대 주제별 개별 인터랙티브 탐색 📚

본 문서는 [pre_project_summary.md](pre_project_summary.md) 및 사용자 요청사항에 따라, 백엔드 핵심 개념 83개 이상에 대한 정리와 더불어 **19가지 핵심 세부 주제별 전용 인터랙티브 웹 페이지**로 연결되는 직관적인 가이드를 제공합니다.

---

## 🚀 19대 백엔드 세부 주제별 개별 웹 페이지 바로가기

아래의 링크를 클릭하면 해당 주제만 단독으로 시각화 및 시뮬레이션하여 다루는 전용 HTML 페이지로 이동합니다:

| 번호 | 주제 | 개별 웹 페이지 링크 | 시각화 & 주요 포함 설명 |
| :--- | :--- | :--- | :--- |
| **1** | **SPOF (단일 장애점)** | [jargons/spof.html](jargons/spof.html) | SPOF 장애 전파 시뮬레이터 & Multi-AZ Automatic Failover |
| **2** | **MSA 서비스 간 통신** | [jargons/msa-communication.html](jargons/msa-communication.html) | REST API vs gRPC vs Kafka 서비스 통신 시퀀스 애니메이션 |
| **3** | **로드밸런싱 & 수평 확장** | [jargons/load-balancing.html](jargons/load-balancing.html) | Scale-up vs Scale-out 비교 및 Round-Robin 트래픽 분산 |
| **4** | **EDA & MQTT 비교** | [jargons/eda-mqtt.html](jargons/eda-mqtt.html) | Enterprise Kafka 분산 로그 vs IoT 경량 MQTT Pub/Sub 비교 |
| **5** | **Kafka CDC** | [jargons/kafka-cdc.html](jargons/kafka-cdc.html) | Debezium + Kafka Connect 트랜잭션 로그 실시간 동기화 |
| **6** | **DLQ 예외 처리 프로세스** | [jargons/dlq.html](jargons/dlq.html) | Retry Backoff 횟수 초과 시 DLX 라우팅 및 Redrive 수동 재처리 |
| **7** | **NoSQL 4대 데이터 모델** | [jargons/nosql.html](jargons/nosql.html) | Document, Key-Value, Wide-Column, Graph 특징 매트릭스 & 실무 예시 |
| **8** | **MongoDB 실무 사용 사례** | [jargons/mongodb-usage.html](jargons/mongodb-usage.html) | JSON 결제 페이로드 & Aggregation Pipeline 연산 실습 |
| **9** | **VectorDB & RAG** | [jargons/vectordb.html](jargons/vectordb.html) | 1536차원 임베딩 공간 Cosine 유사도 시맨틱 검색 Visualizer |
| **10** | **Sharding-JDBC** | [jargons/sharding-jdbc.html](jargons/sharding-jdbc.html) | Sharding Key 기준 DB 수평 분할 라우터 시뮬레이터 & Spring 코드 |
| **11** | **JPA vs QueryDSL** | [jargons/jpa-querydsl.html](jargons/jpa-querydsl.html) | JPQL 문자열 vs QueryDSL 타입-세이프 Fluent API 코드 비교 |
| **12** | **PG vs VAN 다이어그램** | [jargons/pg-van.html](jargons/pg-van.html) | 사용자 ➔ 가맹점 ➔ PG ➔ VAN ➔ 카드사 승인 흐름도 |
| **13** | **펌뱅킹 (Firm Banking)** | [jargons/firmbanking.html](jargons/firmbanking.html) | 은행 전산망 실시간 이체 & 자정 일괄 정산 프로세스 예시 |
| **14** | **ELK 스택 사례 & 다이어그램** | [jargons/elk.html](jargons/elk.html) | Logstash 수집 ➔ ES 인덱싱 ➔ Kibana 관제 시각화 |
| **15** | **ELK vs OTel vs LGTM** | [jargons/observability.html](jargons/observability.html) | 로그 중심(ELK) vs OpenTelemetry 표준 vs Grafana(LGTM) 비교 |
| **16** | **REST vs GraphQL vs gRPC** | [jargons/api-protocols.html](jargons/api-protocols.html) | Overfetching 비교 & HTTP/1.1 vs HTTP/2 프레임 시각화 |
| **17** | **WebSocket vs STOMP** | [jargons/websocket-stomp.html](jargons/websocket-stomp.html) | 순수 TCP 소켓 vs STOMP SEND/SUBSCRIBE 헤더 프레임 구조 |
| **18** | **Node.js vs NestJS vs Next.js**| [jargons/js-ecosystem.html](jargons/js-ecosystem.html) | 런타임(Node) vs 백엔드(Nest) vs 풀스택/SSR(Next) 역할 구분 |
| **19** | **TDD 사례 & 워크플로우** | [jargons/tdd-cases.html](jargons/tdd-cases.html) | Red-Green-Refactor 사이클 시뮬레이터 & JUnit 5 실습 코드 |

---

## 🌐 대화형 통합 사전 웹 페이지
모든 용어를 한 곳에서 카테고리별로 검색하고 위의 19대 주제별 전용 페이지로 이동할 수 있는 대문 페이지는 **[jargons.html](jargons.html)** 에서 확인할 수 있습니다.
