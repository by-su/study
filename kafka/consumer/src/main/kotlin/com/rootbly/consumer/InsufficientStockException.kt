package com.rootbly.consumer

class InsufficientStockException(
    val productId: Long,
    val requestedQuantity: Int,
    val availableQuantity: Int
) : RuntimeException("재고 부족: 상품 $productId, 요청 $requestedQuantity, 재고 $availableQuantity")