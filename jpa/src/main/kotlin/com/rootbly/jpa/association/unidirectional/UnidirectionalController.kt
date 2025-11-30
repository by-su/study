package com.rootbly.jpa.association.unidirectional

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UnidirectionalController(
    private val authorTwoRepository: AuthorTwoRepository
) {

    /**
     * 양방향 관계로 선언하지 않으면
     * 1. 중간 테이블이 생긴다.
     * 2. 중간 테이블에도 데이터를 넣기 때문에 insert, delete쿼리가 더 많이 나간다.
     *
     * 새로운 book을 추가할 때 모두 삭제하고 모두 다시 추가하는 형식
     * 삭제도 똑같음. 모두 삭제하고 다시 insert함
     */
    @PostMapping("unidirection")
    fun unidirectionalTest() {
        val jn = AuthorTwo(
            age = 15,
            genre = "horror",
            name = "jn"
        )

        val jn01 = BookTwo(
            isbn = "1",
            title = "test1"
        )
        val jn02 = BookTwo(
            isbn = "2",
            title = "test2"
        )
        val jn03 = BookTwo(
            isbn = "3",
            title = "test3"
        )

        jn.addBook(jn01)
        jn.addBook(jn02)
        jn.addBook(jn03)

        authorTwoRepository.save(jn)
    }
}