## topic 생성
```bash
kafka-topics --create \
--bootstrap-server localhost:9092 \
--topic my-first-topic \
--partitions 3 \
--replication-factor 1
```
- --partitions 3: 3개의 파티션으로 분할 (병렬 처리)
- --replication-factor 1: 복제본 1개 (단일 노드라서)

## topic 목록 확인
```bash
kafka-topics --list --bootstrap-server localhost:9092
```

## topic 상세 정보 보기
```bash
kafka-topics --describe --bootstrap-server localhost:9092 --topic test-topic
```

## Producer로 메세지 보내기
### producer 실행하기
```text
kafka-console-producer --bootstrap-server localhost:9092 --topic my-first-topic
```

### key가 있는 메세지 보내기 
```text
kafka-console-producer --bootstrap-server localhost:9092 --topic orders --property "parse.key=true" --property "key.separator=:"
```

## Consumer 
### Consumer 실행
```text
kafka-console-consumer --bootstrap-server localhost:9092 --topic my-first-topic
```
- 데이터가 보이지 않는다. 연결된 이후 부터 들어온 메세지만 읽기 때문

```text
kafka-console-consumer \
  --bootstrap-server localhost:9092 \
  --topic my-first-topic \
  --from-beginning
```
`--from-begining`을 이용해 처음부터 데이터를 받을 수 있다.


### Consumer Group 관리
#### Consumer Group 목록 보기
```text
kafka-consumer-groups \
  --bootstrap-server localhost:9092 \
  --list
```

#### Consumer Group 상세 정보
```text
kafka-consumer-group \
  --bootstrap-server localhost:9092 \
  --describe \
  --group console-consumer-71923
```

