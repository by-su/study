package com.rootbly.producer

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class Order(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val productId: Long,
    val quantity: Int,
    val customerId: Long,

    @Enumerated(EnumType.STRING)
    val status: OrderStatus = OrderStatus.CREATED,

    var eventPublished: Boolean = false,

    val createdAt: LocalDateTime = LocalDateTime.now()
) {
}

enum class OrderStatus {
    CREATED, COMPLETED, FAILED
}