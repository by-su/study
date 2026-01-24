package com.rootbly.notification

import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class NotificationEventConsumer(
    private val notificationService: NotificationService
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(topics = ["order-created"], groupId = "notification-service")
    fun handleOrderCreated(event: OrderCreatedEvent) {
        logger.info("주문 생성 이벤트 수신: orderId = ${event.orderId}")

        notificationService.sendOrderSuccessNotification(
            customerId = event.customerId,
            orderId = event.orderId
        )
    }

    @KafkaListener(topics = ["stock-insufficient"], groupId = "notification-service")
    fun handleStockInsufficient(event: StockInsufficientEvent) {
        logger.info("재고 부족 이벤트 수신: orderId=${event.orderId}")

        notificationService.sendStockInsufficientNotification(
            customerId = event.customerId,
            orderId = event.orderId,
            productId = event.productId,
            requestedQuantity = event.requestedQuantity,
            availableQuantity = event.availableQuantity
        )
    }

}