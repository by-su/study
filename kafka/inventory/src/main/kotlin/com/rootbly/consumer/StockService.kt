package com.rootbly.consumer

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StockService(
    private val stockRepository: StockRepository
) {

    private val logger = LoggerFactory.getLogger(javaClass)
    
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

    private var attemptCount = 0

    @Transactional
    fun dlqSimulate(productId: Long, quantity: Int) {
        attemptCount++

        logger.info("재고 차감 시도 #$attemptCount - productId: $productId")

        if (attemptCount <= 5) {
            logger.warn("일시적 DB 연결 실패 시뮬레이션 - 시도: $attemptCount")
            throw RuntimeException("DB 연결 실패 (테스트)")
        }

        logger.info("재고 차감 시도 성공!")

        val stock = stockRepository.findByIdWithLock(productId)
            ?: throw IllegalArgumentException("Stock not found: $productId")

        if (stock.quantity < quantity) {
            throw InsufficientStockException(
                productId = productId,
                requestedQuantity = quantity,
                availableQuantity = stock.quantity
            )
        }

        stock.quantity -= quantity
        stockRepository.save(stock)

        attemptCount = 0  // 성공 시 카운트 리셋
    }
}