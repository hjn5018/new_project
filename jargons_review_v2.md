# msa-communication
- 헤더 중에서도 Bearer가 특히 무거운지, 다른 요소들은 어떤지 설명해줘.
- Protobuf와 멀티플렉싱 설명에서 예시를 들어줬으면 좋겠어.
- HTTP/1.1과 HTTP/2의 기초적인 설명이 추가됐으면 좋겠어.

# eda-mqtt
- MQTT, RabbitMQ, Kafka의 동작 원리를 시각적으로 확인할 수 있으면 좋겠어.
- QoS에서 publish하는 게 Client가 맞니? Broker가 publish하는 건 아닌지 헷갈려. 이에 대한 설명도 있으면 좋겠어.
- QoS 1에서 도어락 예시를 이해하기 어려워. 쉽게 설명해줘.
- At most once, At least once, Exactly once에서 무엇이 대상인지 알기 어려워. 설명해줘.
- QoS 2는 특히 이해하기 어려워. 더 자세한 설명이 필요해.

# kafka-cdc
- 파이프라인에서 나오는 용어들이 어려워. Binlog, WAL, 로그 테일링, Kafka Topic, Avro, Target Sinks, Snowflake, DW등 용어에 대한 설명들이 같이 있으면 좋겠어.

# dlq
- dlq가 발생하는 시나리오가 와닿지 않아. 자주 접할 수 있는 상황을 실감있게 알아볼 수 있도록 해줘.

# nosql
- Redis 로그인 세션 예시에서 TTL 캐시 처리가 잘 드러나지 않는 것 같아. 수정해줘.
- Wide-Column에 대한 예시가 잘 이해되지 않아. CQL이 무엇인지, 이 구조를 어떻게 활용하는지에 대한 설명이 더 필요해.
- Cypher 문법을 본 적이 처음이야. 추가적인 설명이 필요해.

# vectordb
- pgvector에서 sql 테이블과 벡터 검색을 join한다는 개념이 이해되지 않아. 설명이 필요해.
- RAG 파이프라인에서 vectorDB 검색까지는 이해됐는데, Top-2 유사 문서 조각을 추출한 후에 문맥과 질문을 합쳐서 LLM 전송한다는 부분이 이해되지 않아. 자세한 설명이 필요해.

# sharding-jdbc
시각적인 다이어그램으로 이해할 수 있으면 좋겠어.

# observability
- ELK Stack은 대용량 전문인 이유와, 별도 APM 라이브러리는 어떤 것이 있는지, 전문 인덱싱이 무엇이며 디스크와 메모리 비용이 큰 이유는 무엇인지가 필요해.
- OTel이 벤더 독립적이라는 게 무슨 말인지 설명이 필요해.
- Loki 레이블 인덱싱이 어떻게 비용을 절감하는지에 대한 설명이 필요해.

# api-protocols
- 각 프로토콜들의 예시를 들어줘. 시각적인 설명도 추가해주면 좋겠어.

# websocket-stomp
- websocket과 stomp 각각의 예시를 들어줘.
- 핵심 차이점들에서 중요한 개념들(TCP 3-way handshake, http handshake, 양방향 파이프, transport protocol, 데이터 파싱, Spring messageBroker와 Redis의 적용 예시)을 포함해서 설명해줘.