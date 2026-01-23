package com.rootbly.producer

import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class OrderEventPublisher(
    private val kafkaTemplate: KafkaTemplate<String, OrderCreatedEvent>,
    private val orderRepository: OrderRepository
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handleOrderCreated(event: OrderCreatedInternalEvent) {
        val order = event.order

        try {
            val kafkaEvent = OrderCreatedEvent(
                orderId = order.id!!,
                productId = order.productId,
                quantity = order.quantity,
                customerId = order.customerId
            )

            kafkaTemplate.send("order-created", kafkaEvent).get()

            order.eventPublished = true
            orderRepository.save(order)

            logger.info("Order event published: ${order.id}")

        } catch (e: Exception) {
            logger.error("Failed to publish order event: ${order.id}", e)
            // eventPublished = false 유지 (배치가 재발행)
        }
    }
}