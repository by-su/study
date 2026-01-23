package com.rootbly.consumer

import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class OrderEventConsumer(
    private val stockService: StockService,
    private val kafkaTemplate: KafkaTemplate<String, StockInsufficientEvent>
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(topics = ["order-created"], groupId = "inventory-service")
    fun handleOrderCreated(event: OrderCreatedEvent) {
        try {
            stockService.decreaseStock(event.productId, event.quantity)
            logger.info("재고 차감 성공: orderId=${event.orderId}, productId=${event.productId}")

        } catch (e: InsufficientStockException) {
            logger.warn("재고 부족: ${e.message}")

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