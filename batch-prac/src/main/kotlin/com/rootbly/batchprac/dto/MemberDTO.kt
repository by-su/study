package com.rootbly.batchprac.dto

data class MemberDTO(
    val id: Long,
    val name: String,
    val email: String,
    val age: Int,
    val active: Boolean
)
