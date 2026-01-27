package com.rootbly.consumer

import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Component

@Component
class OrderEventConsumer(
    private val stockService: StockService,
    private val kafkaTemplate: KafkaTemplate<String, StockInsufficientEvent>
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(topics = ["order-created"], groupId = "inventory-service", concurrency = "3")
    fun handleOrderCreated(
        event: OrderCreatedEvent,
        @Header(KafkaHeaders.RECEIVED_PARTITION) partition: Int
    ) {
        logger.info("[파티션-$partition][${Thread.currentThread().name}] 메시지 수신: orderId=${event.orderId}, customerId=${event.customerId}")

        try {
            stockService.dlqSimulate(event.productId, event.quantity)
            logger.info("[파티션-$partition][${Thread.currentThread().name}] 재고 차감 성공: orderId=${event.orderId}")

        } catch (e: InsufficientStockException) {
            logger.warn("[파티션-$partition][${Thread.currentThread().name}] 재고 부족: ${e.message}")

            // 재고 부족 이벤트 발행
            val insufficientEvent = StockInsufficientEvent(
                orderId = event.orderId,
                productId = event.productId,
                requestedQuantity = event.quantity,
                availableQuantity = e.availableQuantity,
                customerId = event.customerId
            )

            kafkaTemplate.send("stock-insufficient", event.orderId.toString(), insufficientEvent)
        }
    }
}