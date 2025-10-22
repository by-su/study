package com.rootbly.batchprac.shop

data class ShopRankSnapShot(
    val shopId: Long,
    val totalSalePrice: Long,
    val rank: Int
)