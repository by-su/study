package com.rootbly.batchprac.domain

data class Member(
    val id: Long,
    val name: String,
    val email: String,
    val age: Int,
    val active: Boolean
) {
}