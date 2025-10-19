package com.rootbly.spring.notification

import jakarta.persistence.*

@Entity
@Table(name = "notifications")
class Notification(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val userId: Long,
    val message: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is Notification) return false

        return id != null && id == other.id
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }

    override fun toString(): String {
        return "Notification(id=$id, userId=$userId, message='$message')"
    }
}