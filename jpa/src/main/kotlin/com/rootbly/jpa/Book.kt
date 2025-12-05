package com.rootbly.jpa

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
)