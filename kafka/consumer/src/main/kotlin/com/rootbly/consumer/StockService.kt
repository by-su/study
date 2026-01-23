package com.rootbly.consumer

import org.springframework.stereotype.Service

@Service
class StockService(
    private val stockRepository: StockRepository
) {
    
    fun decreaseStock(productId: Long, quantity: Int) {
        val stock = stockRepository.findById(productId)
            .orElseThrow { IllegalArgumentException("Stock not found") }

        if (stock.quantity < quantity) {
            throw InsufficientStockException(
                productId = productId,
                requestedQuantity = quantity,
                availableQuantity = stock.quantity
            )
        }

        stock.quantity -= quantity
        stockRepository.save(stock)
    }
}