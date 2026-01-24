package com.rootbly.consumer

import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Component

@Component
class DlqConsumer {
    private val logger = LoggerFactory.getLogger(javaClass)
    
    @KafkaListener(topics = ["order-created.DLT"], groupId = "dlq-monitor")
    fun handleDlq(
        event: OrderCreatedEvent,
        @Header(KafkaHeaders.EXCEPTION_MESSAGE) exceptionMessage: String?
    ) {
        logger.error("""
            ==================== DLQ 메시지 수신 ====================
            주문 ID: ${event.orderId}
            상품 ID: ${event.productId}
            수량: ${event.quantity}
            고객 ID: ${event.customerId}
            에러 메시지: $exceptionMessage
            ========================================================
        """.trimIndent())
    }
}