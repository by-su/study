package com.rootbly.jpa.association.manytomany

import jakarta.persistence.EntityManager
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ManyToManyController(
    private val manyToManyTestService: ManyToManyTestService,
    private val entityManager: EntityManager
) {

    @GetMapping("/manytomany")
    fun manyToMany(): ResponseEntity<Map<String, Any?>> {
        // 1. 테스트 데이터 설정
        val bookId = manyToManyTestService.setupTestData()

        entityManager.clear()

        // 2. Book 조회 (authors가 OrderBy로 정렬되어 로드됨)
        val book = manyToManyTestService.getBookWithAuthors(bookId)

        // 3. Authors 정보 추출 (순서 확인용)
        val authorsInfo = book.authors.map { author ->
            mapOf(
                "id" to author.id,
                "name" to author.name,
                "age" to author.age,
                "genre" to author.genre
            )
        }

        // 4. 결과 반환
        val response = mapOf(
            "bookId" to book.id,
            "title" to book.title,
            "isbn" to book.isbn,
            "authorsCount" to book.authors.size,
            "authors" to authorsInfo,
            "orderByWorking" to isOrderedByName(book.authors),
            "message" to if (isOrderedByName(book.authors))
                "✅ OrderBy is working correctly! Authors are sorted by name ASC"
            else
                "❌ OrderBy is NOT working. Authors are not sorted"
        )

        return ResponseEntity.ok(response)
    }

    private fun isOrderedByName(authors: Set<AuthorThree>): Boolean {
        val names = authors.map { it.name }
        val sortedNames = names.sorted()
        return names == sortedNames
    }
}