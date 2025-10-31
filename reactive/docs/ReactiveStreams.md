리액티브 스트림은 다음 4가지로 구성되어 있다.
1. Publisher
2. Subscriber
3. Subscription
4. Processor

## Publisher
Flux, Mono

### Mono
> 0개 또는 1개의 데이터를 emit하는 Publisher

에러가 발생하면 onError signal을 emit한다.

### Flux
> 0개 ~ N개의 데이터를 emit하는 Publisher


## 콜드 스퀀스와 핫 스퀀스 

### 콜드 시퀀스
- Subscriber가 구독할 때마다 타임라인의 처음부터 emit된 모든 데이터를 받을 수 있다.

### 핫 스퀀스
- SUbscriber가 구독한 시점부터 emit된 데이터를 받을 수 있다.



## 백프레셔 
publisher에서 emit된느 ㄷ데이터를 subscriber쪽에서 안정적으로 처리하기 위한 제어 기능

= 요청 데이터의 개수를 처리하는 방법 
subscriber가 적절히 처리할 수 있는 수준의 데이터 개수를 publisher에게 요청

= Backpressure 전략을 사용하는 방법
- IGNORE (적용하지 않는다)
- ERROR 전략 (버퍼가 가득차면 Exception 발생)
- DROP 전략 (데이터가 버퍼에 가득 찰 경우, 버퍼 밖에서 대기하는 먼저 emit된 데이터부터 Drop) --> 2개 존재
- LATEST전략 (버퍼에 가득 찰 경우,  버퍼 밖에 대기하는 가장 최근에 emit된 데이터부터 버퍼에 채우는 전략)
- BUFFER전략 (버퍼에 가득 찰 경우, 버퍼 안에 있는 데이터를 Drop시키는 전략)




[ IGNORE ]
```
Publisher: 1 → 2 → 3 → 4 → 5 → 6 → 7 → 8 → 9 → 10 (멈추지 않음)
Subscriber: 1 (처리중...) 
           └─ 나머지는 전부 다운스트림으로 밀어넣음
           └─ 메모리 부족 위험! 💥
```


### DROP전략
[ onBackpressureDrop ]

DROP_LATEST
```text
시간 0ms:   1 도착 → 처리중
시간 10ms:  2 도착 → 버림 (보관 안 함)
시간 20ms:  3 도착 → 버림 (보관 안 함)
...
시간 90ms:  9 도착 → 버림 (보관 안 함)
시간 100ms: 1 처리완료 → 다음 요청 → 10이 마침 이때 도착하면 받음
```

[ DROP_OLDEST ]
```test
시간 0ms:   1 받음 → 처리중
시간 10ms:  2 도착 → [버퍼: 2]
시간 20ms:  3 도착 → [버퍼: 2, 3]
시간 30ms:  4 도착 → [버퍼: 2, 3, 4] (버퍼 가득)
시간 40ms:  5 도착 → 2 버림! ❌ → [버퍼: 3, 4, 5]
시간 50ms:  6 도착 → 3 버림! ❌ → [버퍼: 4, 5, 6]
시간 100ms: 1 완료 → 버퍼에서 4 꺼냄 → 처리
시간 200ms: 4 완료 → 버퍼에서 5 꺼냄 → 처리
시간 300ms: 5 완료 → 버퍼에서 6 꺼냄 → 처리
```


[ onBackpressureLatest ]
```kotlin
시간 0ms:   1 도착 → 처리중
시간 10ms:  2 도착 → [최신값 저장: 2]
시간 20ms:  3 도착 → [최신값 저장: 3] (2는 버림)
시간 30ms:  4 도착 → [최신값 저장: 4] (3은 버림)
...
시간 90ms:  9 도착 → [최신값 저장: 9] (8은 버림)
시간 100ms: 1 처리완료 → 다음 요청 → 보관된 9를 즉시 받음 ✅

```

[ onBackpressureError ]
```
손님(1) 탑승 → 이동중
손님(2) 타려고 함 → 💥 "정원 초과! 운행 중단!"
```

## Sinks
signal을 프로그래밍적으로 push할 수 잇는 기능을 가지고 있는 publisher 
Thread-safe하지 않을 수 있는 processor보다 더 나은 대안. sinks는 Thread-safe하게 signal을 발생 시킨다 



