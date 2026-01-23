package com.rootbly.producer

data class CreateOrderRequest(
    val productId: Long,
    val quantity: Int,
    val customerId: Long
)

data class OrderResponse(
    val orderId: Long,
    val productId: Long,
    val quantity: Int,
    val status: String
)