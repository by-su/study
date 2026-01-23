# 설계 고민

## 주문 이벤트를 발생하는 시점
```kotlin
@PostMapping("/orders")
fun createOrder() {
    orderService.createOrder()  // 여기서 DB 저장

    // 카프카 발행은 언제? 
    // A ) DB 저장 직후?
    // B ) DB 트랜잭션 커밋 후?
```

### 문제 상황
주문 생성 시 DB 저장과 Kafka 이벤트 발행, 두 작업이 모두 성공해야 하는데 어느 한쪽이 실패할 수 있다.


#### A안: 트랜잭션 내부에서 즉시 발행
```kotlin
@Transactional
fun createOrder() {
    orderRepository.save(order)          // 1. DB 저장
    kafkaTemplate.send("order-created")  // 2. Kafka 발행
    // 3. 트랜잭션 커밋 시도
}
```
발생 가능한 문제:
- DB 저장 성공
- Kafka 발행 성공
- 트랜잭션 커밋 **실패**

결과:
- DB롤백이 되어 Consumer는 존재하지 않는 주문에 대해 작업 시도

```kotlin
fun handleOrderCreated(event: OrderCreatedEvent) {
    val order = orderRepository.findById(event.orderId)
    
    if (order == null) {
        // 이게 정상인가? 비정상인가?
        // 1) 아직 DB 커밋 안 됐을 수도 (타이밍 이슈)
        // 2) 진짜 롤백됐을 수도 (A안의 문제)
        // 3) 네트워크 지연으로 조회 실패일 수도
        
        // → 이 3가지를 구분할 방법이 없음
    }
}
```
- 문제 발생 시 빠른 탐지는 가능하지만 해결 방법이 불명확함. (3가지 케이스 구분 불가능)

#### B안: 트랜잭션 커밋 후 발행
```kotlin
@Transactional
fun createOrder() {
    orderRepository.save(order)  // 1. DB 저장
    // 2. 트랜잭션 커밋 (성공 확정)
}
// 3. 커밋 후 kafkaTemplate.send()
```
발생 가능한 문제:
- DB 저장 성공
- 트랜잭션 커밋 성공
- Kafka 발행 실패 (네트워크 오류, 브로커 다운 등)

결과:
- 주문은 DB에 있는데 재고 차감/알림이 안 나감

해결방법:
1. 발행 여부 추저 필드 추가 
```kotlin
@Entity
class Order(
    // ...
    var eventPublished: Boolean = false  // 발행 여부 추적
)
```
2. 배치/스케줄러로 복구
```kotlin
@Scheduled(fixedDelay = 60000)  // 1분마다
fun republishFailedEvents() {
    val unpublishedOrders = orderRepository
        .findByEventPublishedFalse()
    
    unpublishedOrders.forEach { order ->
        kafkaTemplate.send("order-created", order.toEvent())
        order.eventPublished = true
        orderRepository.save(order)
    }
}
```
- 문제 발생 시 즉시 감지는 어렵지만 해결 방법이 명확함


