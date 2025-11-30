package com.rootbly.jpa.association

import org.springframework.data.jpa.repository.JpaRepository

interface AuthorRepository: JpaRepository<Author, Long> {
}