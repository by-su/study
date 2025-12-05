package com.rootbly.jpa

import jakarta.persistence.EntityManager
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest
import org.springframework.test.context.TestPropertySource

@DataJpaTest
@TestPropertySource(properties = [
    "spring.jpa.show-sql=true",
    "spring.jpa.properties.hibernate.format_sql=true",
    "logging.level.org.hibernate.SQL=DEBUG"
])
class EntityGraphTest {

    @Autowired
    private lateinit var authorRepository: AuthorRepository

    @Autowired
    private lateinit var entityManager: EntityManager

    @BeforeEach
    fun setUp() {
        // 테스트 데이터 생성
        val author1 = Author(
            age = 45,
            genre = "Fantasy",
            name = "J.K. Rowling"
        )
        author1.books.add(Book(isbn = "ISBN-001", title = "Harry Potter 1", author = author1))
        author1.books.add(Book(isbn = "ISBN-002", title = "Harry Potter 2", author = author1))
        author1.books.add(Book(isbn = "ISBN-003", title = "Harry Potter 3", author = author1))

        val author2 = Author(
            age = 50,
            genre = "Science Fiction",
            name = "Isaac Asimov"
        )
        author2.books.add(Book(isbn = "ISBN-101", title = "Foundation", author = author2))
        author2.books.add(Book(isbn = "ISBN-102", title = "I, Robot", author = author2))

        authorRepository.saveAll(listOf(author1, author2))
        entityManager.flush()
        entityManager.clear()

        println("\n========== 테스트 데이터 생성 완료 ==========\n")
    }

    @Test
    @DisplayName("EntityGraph 사용 - JOIN으로 한 번에 조회")
    fun testWithEntityGraph() {
        println("\n========== EntityGraph 사용 테스트 시작 ==========")
        println("예상: LEFT JOIN을 사용해서 한 번의 쿼리로 Author와 Books를 함께 조회\n")

        // EntityGraph가 적용된 findAll() 호출
        val authors = authorRepository.findAll()

        println("\n========== 조회 완료 - 이제 books에 접근 ==========\n")

        // books에 접근해도 추가 쿼리가 발생하지 않음
        authors.forEach { author ->
            println("Author: ${author.name}")
            author.books.forEach { book ->
                println("  - Book: ${book.title}")
            }
        }

        println("\n========== EntityGraph 테스트 종료 ==========\n")
    }

}