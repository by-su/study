package com.rootbly.spring.order

import com.rootbly.spring.notification.Notification
import com.rootbly.spring.notification.NotificationRepository
import com.rootbly.spring.payload.DummyCreateRequest
import com.rootbly.spring.user.User
import com.rootbly.spring.user.UserRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service


@Service
class DummyService(
    private val userRepository: UserRepository,
    private val orderRepository: OrderRepository,
    private val notificationRepository: NotificationRepository
) {
    

    fun generateDummyData(request: DummyCreateRequest) {
        val user = userRepository.save(User(name = "User ${request.id}"))
        orderRepository.save(Order(userId = user.id!!, amount = 10))
        notificationRepository.save(Notification(userId = user.id!!, message = "Test notification ${request.id}"))
    }

    fun getDummy(id: Long) {
        val user = userRepository.findById(id).orElseThrow { throw EntityNotFoundException("사용자를 찾을 수 없습니다.") }
        val order = orderRepository.findByUserId(user.id!!)
        val notification = notificationRepository.findByUserId(user.id!!)

        println("User: $user")
        println("Order: $order")
        println("Notification: $notification")
    }
}