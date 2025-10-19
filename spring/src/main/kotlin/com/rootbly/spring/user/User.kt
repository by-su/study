package com.rootbly.spring.user

import jakarta.persistence.*


@Entity
@Table(name = "users")
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val name: String
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is User) return false

        return id != null && id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "User(id=$id, name='$name')"
    }


}