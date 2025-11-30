package com.rootbly.jpa.association

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class BidirectionalController(
    private val authorRepository: AuthorRepository
) {

    @GetMapping("bidirection")
    fun bidirectional() {
        val jn = Author(
            age = 15,
            genre = "horror",
            name = "jn"
        )

        val jn01 = Book(
            isbn = "1",
            title = "test1",
            author = jn
        )
        val jn02 = Book(
            isbn = "2",
            title = "test2",
            author = jn
        )
        val jn03 = Book(
            isbn = "3",
            title = "test3",
            author = jn
        )

        jn.addBook(jn01)
        jn.addBook(jn02)
        jn.addBook(jn03)

        authorRepository.save(jn)
    }
}