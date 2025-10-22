package com.rootbly.batchprac.shop

data class Shop(
    val id: Long,
    val name: String,
    val totalSalePrice: Long = 0
)
