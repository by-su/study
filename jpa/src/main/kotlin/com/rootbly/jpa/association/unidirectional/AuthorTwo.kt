package com.rootbly.jpa.association.unidirectional

import jakarta.persistence.*


@Entity
class AuthorTwo(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val age: Int,
    val genre: String,
    val name: String,

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    val books: MutableList<BookTwo> = mutableListOf()
) {
    fun addBook(book: BookTwo) {
        this.books.add(book)
    }
}