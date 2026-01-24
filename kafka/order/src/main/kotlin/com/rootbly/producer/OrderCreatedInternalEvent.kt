package com.rootbly.producer

data class OrderCreatedInternalEvent(
    val order: Order
)