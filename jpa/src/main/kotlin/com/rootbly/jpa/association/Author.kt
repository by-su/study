package com.rootbly.jpa.association

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
class Author(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val age: Int,
    val genre: String,
    val name: String,

    /**
     * 부모 엔티티에 cascade, orphanRemoval 선언하기 !
     */
    @OneToMany(mappedBy = "author", cascade = [CascadeType.ALL], orphanRemoval = true)
    val books: MutableList<Book> = mutableListOf()
) {

    /**
     * 연관관계 편의 메서드 만들기
     */
    fun addBook(book: Book) {
        this.books.add(book)
        book.author = this
    }

    fun removeBook(book: Book) {
        book.author = null
        this.books.remove(book)
    }

    fun removeBooks(book: Book) {
        val iterator = this.books.iterator()

        while (iterator.hasNext()) {
            val book = iterator.next()

            book.author = null
            iterator.remove()
        }
    }

    /**
     * toString()을 선언할 때는 자식 연관관계는 빼고 정의하기 (LazyInitializationException 발생할 수 있음)
     */
    override fun toString(): String {
        return "Author(id=$id, age=$age, genre='$genre', name='$name')"
    }

}