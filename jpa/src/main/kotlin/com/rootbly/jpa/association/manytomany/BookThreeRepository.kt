package com.rootbly.jpa.association.manytomany

import org.springframework.data.jpa.repository.JpaRepository

interface BookThreeRepository: JpaRepository<BookThree, Long> {
}