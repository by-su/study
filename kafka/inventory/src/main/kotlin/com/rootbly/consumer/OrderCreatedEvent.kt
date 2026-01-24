package com.rootbly.consumer

data class OrderCreatedEvent(
    val orderId: Long,
    val productId: Long,
    val quantity: Int,
    val customerId: Long
)