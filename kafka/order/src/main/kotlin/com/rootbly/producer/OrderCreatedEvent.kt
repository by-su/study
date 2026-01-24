package com.rootbly.producer

data class OrderCreatedEvent(
    val orderId: Long,
    val productId: Long,
    val quantity: Int,
    val customerId: Long,
    val status: String = "CREATED"
)