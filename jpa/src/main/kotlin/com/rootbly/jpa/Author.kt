package com.rootbly.jpa

import jakarta.persistence.*


@Entity
@NamedEntityGraph(
    name = "author-books-graph",
    attributeNodes = [
        NamedAttributeNode("books")
    ]
)
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

}