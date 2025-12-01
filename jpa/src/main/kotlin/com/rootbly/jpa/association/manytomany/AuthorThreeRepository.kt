package com.rootbly.jpa.association.manytomany

import org.springframework.data.jpa.repository.JpaRepository

interface AuthorThreeRepository: JpaRepository<AuthorThree, Long> {
}