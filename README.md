# 백엔드 커리어 성장을 위한 신규 프로젝트 구축 레포지토리 🚀

본 저장소는 백엔드 개발자 역량 강화 및 채용 공고(JD)의 주요 기술 요구사항을 반영하여, 용어 정리부터 소규모 기술 PoC, 프로젝트 후보군 도출, 그리고 최종 프로젝트 상세 설계까지의 전 과정을 기록하고 관리하는 프로젝트입니다.

---

## 📌 주요 목차 및 문서 바로가기

| 분류 | 주요 내용 | 문서/페이지 링크 |
| :--- | :--- | :--- |
| 📘 **용어 및 개념 정리** | 백엔드/금융/AI/DevOps 83+ 핵심 용어 체계화 | [jargons.md](jargons.md) / [jargons.html](jargons.html) |
| 🧪 **용어 실습 PoC** | 주요 기술 개별/그룹 단위 테스트 소규모 프로젝트 | [jargons.md#개별-용어-실습-poc](jargons.md#개별-용어-실습-poc) |
| 💡 **프로젝트 후보군** | 실무급 프로젝트 후보 3종 및 UI 와이어프레임 | [project_candidates.md](project_candidates.md) / [project_candidates.html](project_candidates.html) |
| 🎯 **최종 선정 프로젝트** | NoSQL, VectorDB, Kafka, RabbitMQ, TDD, 결제, Redis 기반 프로젝트 상세 | [selected_project_details.md](selected_project_details.md) |
| 🗺️ **실행 계획 & 로드맵** | 3-File System 기반 단계별 개발 로드맵 & GCP 배치안 | [selected_project_plan.md](selected_project_plan.md) |

---

## 🛠️ 필수 수록 기술 스택 (Must-Have Stack)

본 프로젝트 라인업 및 최종 선택 프로젝트는 다음의 필수 백엔드 핵심 기술들을 포괄하여 구현 및 검증하도록 설계되었습니다:

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
- [x] 대화형 용어 탐색기 [jargons.html](jargons.html) 구축
- [x] 용어 검증용 5대 소규모 micro-project / PoC 가이드 정리
- [x] 3대 프로젝트 후보군 및 인터랙티브 와이어프레임 [project_candidates.html](project_candidates.html) 완성
- [x] [selected_project_details.md](selected_project_details.md) 및 [selected_project_plan.md](selected_project_plan.md) 최종 설계 완료
- [x] Git 버전 관리 및 GitHub 레포지토리 (`https://github.com/hjn5018/new_project`) 원격 동기화

---

## 🌐 GitHub Repository
- Remote URL: [https://github.com/hjn5018/new_project](https://github.com/hjn5018/new_project)
