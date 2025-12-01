package com.rootbly.jpa.association.manytomany

import jakarta.persistence.*

@Entity
class AuthorThree(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val age: Int,
    val genre: String,
    val name: String,

    /**
     * cascade는 CascadeALL 을 사용하지 말 것. 명시적으로 PERSIST, MERGE사용
     * JoinTable을 이용해 명시적으로 테이블 이름과 컬럼명 명시해주기
     * List말고 Set사용하기
     */
    @ManyToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinTable(
        name = "author_book",
        joinColumns = [JoinColumn(name = "author_id")],
        inverseJoinColumns = [JoinColumn(name = "book_id")]
    )
    val books: MutableSet<BookThree> = hashSetOf()
) {

    /**
     * 더 많이 상호작용할 것 같은 엔티티에 헬퍼 메서드 만들기
     */
    fun addBook(book: BookThree) {
        this.books.add(book)
        book.authors.add(this)
    }

    fun removeBook(book: BookThree) {
        book.authors.remove(this)
        this.books.remove(book)
    }

    fun removeBooks() {
        val iterator = this.books.iterator()

        while (iterator.hasNext()) {
            val book = iterator.next()

            book.authors.remove(this)
            iterator.remove()
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AuthorThree

        return id != null && id == other.id
    }

    override fun hashCode(): Int {
        return 2025
    }

    override fun toString(): String {
        return "AuthorThree(id=$id, age=$age, genre='$genre', name='$name', books=$books)"
    }

}