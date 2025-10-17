package com.rootbly.batchprac.dto

import com.rootbly.batchprac.domain.Member

data class MemberDTO(
    val id: Long,
    val name: String,
    val email: String,
    val age: Int,
    val active: Boolean
) {
    fun toEntity() = Member(
        this.id + 1234567,
        this.name,
        this.email,
        this.age,
        this.active
    )
}
