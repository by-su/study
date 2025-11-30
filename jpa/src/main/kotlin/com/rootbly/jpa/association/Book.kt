package com.rootbly.jpa.association

import jakarta.persistence.*

@Entity
class Book(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val isbn: String,
    val title: String,
    /**
     * 자식에는 FetchType.Lazy 선언해주기
     * JoinColumn으로 외래키 이름 정의해주기
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    var author: Author?
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Book

        return id != null && id == other.id
    }

    override fun hashCode(): Int {
        return 2025
    }
}