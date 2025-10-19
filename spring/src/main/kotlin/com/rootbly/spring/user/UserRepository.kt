package com.rootbly.spring.user

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>