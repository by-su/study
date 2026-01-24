package com.rootbly.producer

import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
){

    @Transactional
    fun createOrder(request: CreateOrderRequest): Order {
        val order = Order(
            productId = request.productId,
            quantity = request.quantity,
            customerId = request.customerId
        )

        val savedOrder = orderRepository.save(order)

        applicationEventPublisher.publishEvent(
            OrderCreatedInternalEvent(savedOrder)
        )

        return savedOrder
    }
}