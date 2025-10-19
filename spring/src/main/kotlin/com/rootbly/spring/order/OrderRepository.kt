package com.rootbly.spring.order

import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Order, Long> {
    fun findByUserId(id: Long): Order?
}