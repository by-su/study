package com.rootbly.consumer

data class StockInsufficientEvent(
    val orderId: Long,
    val productId: Long,
    val requestedQuantity: Int,
    val availableQuantity: Int,
    val customerId: Long
)