package com.rootbly.consumer

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "stocks")
class Stock(
    @Id
    val productId: Long,
    
    var quantity: Int,
    
    val updatedAt: LocalDateTime = LocalDateTime.now()
)
