package com.rootbly.spring.order

import jakarta.persistence.*

@Entity
@Table(name = "orders")
class Order(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val userId: Long,
    val amount: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is Order) return false

        return id != null && id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Order(id=$id, userId=$userId, amount=$amount)"
    }
}
