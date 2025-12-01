package com.rootbly.jpa.association.manytomany

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ManyToManyTestService(
    private val authorRepository: AuthorThreeRepository,
    private val bookRepository: BookThreeRepository
) {
    
    fun setupTestData(): Long {
        // Author 생성 (이름을 역순으로 저장하여 OrderBy 테스트)
        val authorCharlie = AuthorThree(
            age = 45,
            genre = "Fiction",
            name = "Charlie"
        )
        
        val authorAlice = AuthorThree(
            age = 35,
            genre = "Mystery",
            name = "Alice"
        )
        
        val authorBob = AuthorThree(
            age = 40,
            genre = "Thriller",
            name = "Bob"
        )
        
        // Book 생성
        val book = BookThree(
            isbn = "978-1234567890",
            title = "Test Book"
        )
        
        // 관계 설정 (역순으로 추가)
        authorCharlie.addBook(book)
        authorBob.addBook(book)
        authorAlice.addBook(book)

        // 저장
        authorRepository.saveAll(listOf(authorCharlie, authorAlice, authorBob))
        bookRepository.save(book)
        
        return book.id!!
    }
    
    @Transactional(readOnly = true)
    fun getBookWithAuthors(bookId: Long): BookThree {
        return bookRepository.findById(bookId)
            .orElseThrow { IllegalArgumentException("Book not found") }
    }
}