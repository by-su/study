package com.rootbly.producer

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/orders")
class OrderController(
    private val orderService: OrderService
) {
    
    @PostMapping
    fun createOrder(@RequestBody request: CreateOrderRequest): ResponseEntity<Order> {
        val response = orderService.createOrder(request)
        return ResponseEntity.ok(response)
    }
}