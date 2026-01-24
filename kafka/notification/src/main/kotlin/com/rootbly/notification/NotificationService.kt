package com.rootbly.notification

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class NotificationService {
    private val logger = LoggerFactory.getLogger(javaClass)
    
    fun sendOrderSuccessNotification(customerId: Long, orderId: Long) {
        logger.info("[알림] 고객 $customerId: 주문 $orderId 가 성공적으로 접수되었습니다.")
    }
    
    fun sendStockInsufficientNotification(
        customerId: Long, 
        orderId: Long, 
        productId: Long,
        requestedQuantity: Int,
        availableQuantity: Int
    ) {
        logger.info("[알림] 고객 $customerId: 주문 $orderId 가 재고 부족으로 처리되지 못했습니다. " +
                    "(상품: $productId, 요청: $requestedQuantity, 재고: $availableQuantity)")
    }
}