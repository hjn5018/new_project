# msa-communication
- HOL 블로킹이 무엇인지 예시와 함께 부연 설명해줘.
- HTTP/1.1과 HTTP/2의 차이를 분명히 알 수 있도록 시각적으로 표현해줘. js/css를 이용하면 좋을 것 같아.
- HPACK 압축이 무엇인지 부연 설명해줘.
- jwt.io와 같은 사이트에서 alg, typ, sub, name, admin, iat, secret을 통해 172byte 정도의 jwt를 생성하는 예시를 보여주고 있어. 그런데 500 ~ 2,000byte까지 커질 수가 있는 지 의문스러워. 이에 대한 설명을 추가해줘.
- 동기 대기에서 결제 서비스의 1초 지연으로 주문 서버 스레드 풀(200개)이 1초만에 고갈되는 이유가 무엇인지 설명해줘.

# eda-mqtt
- MQTT, RabbitMQ, Kafka의 동작 원리를 시각적으로 확인할 수 있으면 좋겠어. 텍스트만으로 이해하는 건 어려워. js/css를 통해 표현해줘. 다이어그램이나 애니메이션을 넣는 게 좋겠어.
- 질문 1과 질문 2는 QoS 설명의 하단에 들어가도록 해줘.
- 4-way handshake를 사용하는 이유와 3-way handshake와의 차이를 설명해줘.

# kafka-cdc
- Binlog와 WAL은 MySQL이나 PostgreSQL에서 아무런 설정 없이도 기본적으로 적용되어서 일반적인 유저가 확인할 수 있는 설정인지도 알려줘.

# dlq
- 외부 시스템 일시 점검 시나리오가 간략한 것 같아. 더 자세한 설명이 필요해.

# observability
- New Relic, Grafana Tempo, Datadog, CloudWatch 등에 대한 설명이 필요해.
- S3와 GCS가 매우 저렴하다는 이유를 부연 설명해줘.

# api-protocols
- 각 프로토콜들의 사용 시나리오를 mermaid와 같은 다이어그램으로 파악해볼 수 있도록 해줘.