# kafka 로컬 환경 세팅하기

## 도커 컴포즈를 활용
```yml
services:
  kafka:
    image: apache/kafka:4.1.1
    container_name: kafka
    ports:
      - "9092:9092"
    environment:
      # 1. KRaft 및 노드 설정
      KAFKA_NODE_ID: 1
      KAFKA_PROCESS_ROLES: 'broker,controller'
      KAFKA_CONTROLLER_QUORUM_VOTERS: '1@kafka:9093'

      # 2. 리스너 설정 (내부 통신 및 외부 접속 분리)
      KAFKA_LISTENERS: 'PLAINTEXT://0.0.0.0:9092,CONTROLLER://0.0.0.0:9093'
      KAFKA_ADVERTISED_LISTENERS: 'PLAINTEXT://localhost:9092'
      KAFKA_CONTROLLER_LISTENER_NAMES: 'CONTROLLER'
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: 'CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT'

      # 3. 클러스터 메타데이터 설정
      KAFKA_INTER_BROKER_LISTENER_NAME: 'PLAINTEXT'
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
      KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR: 1

      # 4. 데이터 저장 경로 (컨테이너 내부)
      KAFKA_LOG_DIRS: '/var/lib/kafka/data'

    volumes:
      - kafka_data:/var/lib/kafka/data

volumes:
  kafka_data:
    driver: local
```
#### KRaft 모드 (ZooKeeper 없이 실행)
- KAFKA_PROCESS_ROLES: 'broker,controller': 단일 노드가 브로커와 컨트롤러 역할을 모두 수행
- Kafka 3.0+ 버전부터 지원되는 ZooKeeper 없는 새로운 아키텍처

#### 리스너 설정(접속 포트)
- KAFKA_ADVERTISED_LISTENERS: 'PLAINTEXT://localhost:9092':
  - 중요: 외부에서 접속 시 localhost:9092로 연결
  - 다른 컨테이너와 통신하려면 kafka:9092로 변경 필요

#### 개발/테스트 환경 설정
- REPLICATION_FACTOR: 1 > 복제본 없음(단일 노드용)

#### 데이터 영속성
`kafka_data` 볼륨으로 데이터 저장

### 포트 분리의 이유
클라이언트 포트와 컨트롤러 포트를 분리하는 이유

```plaintext
9092 (클라이언트 포트)     9093 (컨트롤러 포트)
      ↓                           ↓
  외부 접근 허용              내부 전용 (격리)
  Producer/Consumer          Controller 간 통신
```
- 클라이언트 포트: 데이터 송수신을 위한 포트
  - Producer
  - Consumer
  - 일반 애플리케이션

- 컨트롤러 포트: 클러스터 내부 관리를 위한 포트 
  - Kafka 브로커들끼리만 통신



### tip
```text
echo "alias kafka-topics='/opt/kafka/bin/kafka-topics.sh'" >> ~/.bashrc
echo "alias kafka-console-producer='/opt/kafka/bin/kafka-console-producer.sh'" >> ~/.bashrc
echo "alias kafka-console-consumer='/opt/kafka/bin/kafka-console-consumer.sh'" >> ~/.bashrc
echo "alias kafka-consumer-groups='/opt/kafka/bin/kafka-consumer-groups.sh'" >> ~/.bashrc
```