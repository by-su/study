package com.rootbly.batchprac.shop.processor

import com.rootbly.batchprac.shop.Shop
import com.rootbly.batchprac.shop.ShopRankSnapShot
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component
import java.util.concurrent.atomic.AtomicInteger

@Component
class ShopRankProcessor : ItemProcessor<Shop, ShopRankSnapShot> {

    private val rankCounter = AtomicInteger(1)

    override fun process(item: Shop): ShopRankSnapShot {
        val rank = rankCounter.getAndIncrement()
        return ShopRankSnapShot(
            shopId = item.id,
            totalSalePrice = item.totalSalePrice,
            rank = rank
        )
    }

    fun reset() {
        rankCounter.set(1)
    }
}