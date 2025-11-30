package com.rootbly.jpa.association.unidirectional

import org.springframework.data.jpa.repository.JpaRepository

interface AuthorTwoRepository: JpaRepository<AuthorTwo, Long> {
}