package com.rootbly.notification

data class OrderCreatedEvent(
    val orderId: Long,
    val productId: Long,
    val quantity: Int,
    val customerId: Long
)