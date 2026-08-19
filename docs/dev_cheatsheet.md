# 🛠️ 백엔드 개발자 생산성 치트시트 & IDE 꿀팁 가이드

본 문서는 Antigravity IDE(VS Code 기반)와 IntelliJ IDEA를 오가며 개발할 때 꼭 필요한 핵심 단축키, Docker/Redis 조작 명령어, Gradle 빌드 팁을 정리한 가이드입니다.

---

## ⚡ 1. IDE 핵심 단축키 비교표 (Antigravity vs IntelliJ)

| 기능 | Antigravity / VS Code | IntelliJ IDEA | 설명 및 꿀팁 |
| :--- | :--- | :--- | :--- |
| **빠른 수정 & 자동 Import** | `Ctrl + .` (마침표) | `Alt + Enter` | 빨간 밑줄 에러 해결 및 클래스 즉시 import |
| **전체 Import 일괄 정리** | `Shift + Alt + O` | `Ctrl + Alt + O` | 빠진 import 자동 추가 & 안 쓰는 import 일괄 삭제 |
| **코드 자동 정렬 (Format)** | `Shift + Alt + F` | `Ctrl + Alt + L` | 들여쓰기 및 줄바꿈 표준 스타일 자동 정리 |
| **파일 전체 빠른 검색** | `Ctrl + P` | `Shift` 두 번 연타 | 파일명으로 프로젝트 내 파일 즉시 열기 |
| **클래스/메서드 정의로 이동** | `F12` 또는 `Ctrl + 클릭` | `Ctrl + B` 또는 `Ctrl + 클릭` | 해당 클래스나 함수의 선언부/구현체로 순간이동 |
| **변수/메서드 이름 일괄 변경**| `F2` | `Shift + F6` | 프로젝트 전체에서 해당 이름을 안전하게 리팩토링 |
| **한 줄 복사 & 붙여넣기** | `Shift + Alt + ↓` | `Ctrl + D` | 현재 커서의 줄을 바로 아래로 복제 |
| **줄 위/아래로 이동** | `Alt + ↑ / ↓` | `Shift + Alt + ↑ / ↓` | 선택한 줄을 통째로 위아래로 이동 |
| **단어 단위 다중 커서 선택** | `Ctrl + D` | `Alt + J` | 동일한 단어를 하나씩 추가 선택해 동시 수정 |
| **터미널 열기/닫기** | ``Ctrl + ` `` (백틱) | `Alt + F12` | 내장 터미널 창 토글 |

---

## 🐳 2. Docker & Redis 터미널 필수 치트시트

```bash
# 1. Redis 컨테이너 백그라운드 기동
docker compose up -d

# 2. Redis 컨테이너 종료 (데이터 볼륨 유지)
docker compose down

# 3. 현재 켜져 있는 컨테이너 상태 및 포트 확인
docker ps

# 4. Redis 상태 단발성 핑(Ping) 체크
docker exec poc1-redis redis-cli ping

# 5. Redis 대화형 콘솔(CLI) 방 안으로 직접 입장
docker exec -it poc1-redis redis-cli

# 6. Redis 내부 데이터 실시간 모니터링 (대기열/락 확인 시 유용)
docker exec -it poc1-redis redis-cli monitor
```

---

## 📦 3. Gradle Wrapper (`gradlew`) 핵심 명령어

컴퓨터에 Gradle이 설치되어 있지 않아도 프로젝트 폴더에서 바로 실행할 수 있는 명령어입니다:

```powershell
# Windows (PowerShell/CMD)
.\gradlew.bat build          # 전체 프로젝트 컴파일 및 패키징
.\gradlew.bat test           # JUnit 동시성/단위 테스트 실행
.\gradlew.bat bootRun        # 스프링 부트 서버 로컬 기동
.\gradlew.bat clean          # 이전 빌드 캐시 및 build/ 폴더 완전 초기화

# Mac / Linux
./gradlew build
./gradlew test
./gradlew bootRun
```

---

## 💡 4. 유용한 스프링 부트 개발 팁

* **자동 Import 설정**: Antigravity/VS Code 설정(`Ctrl + ,`)에서 `java.completion.importOrder`나 `editor.formatOnSave: true`를 켜두면 파일을 저장(`Ctrl + S`)할 때마다 코드가 자동으로 정렬되고 import가 정리됩니다.
* **H2 웹 콘솔 접속**: 스프링 부트 실행 후 브라우저에서 `http://localhost:8080/h2-console` 접속 ➔ JDBC URL: `jdbc:h2:mem:poc1db`, User: `sa`, PW: (빈칸)
