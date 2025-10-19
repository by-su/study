package com.rootbly.spring.notification

import org.springframework.data.jpa.repository.JpaRepository

interface NotificationRepository : JpaRepository<Notification, Long> {
    fun findByUserId(id: Long): Notification?
}