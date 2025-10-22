package com.rootbly.batchprac.shop.writer

import com.rootbly.batchprac.shop.ShopRankSnapShot
import com.rootbly.batchprac.shop.mapper.ShopMapper
import org.slf4j.LoggerFactory
import org.springframework.batch.item.Chunk
import org.springframework.batch.item.ItemWriter
import org.springframework.stereotype.Component

@Component
class ShopRankWriter(
    private val shopMapper: ShopMapper
) : ItemWriter<ShopRankSnapShot> {

    override fun write(chunk: Chunk<out ShopRankSnapShot>) {
        if (chunk.items.isNotEmpty()) {
            shopMapper.bulkUpsertShopRankSnapshot(chunk.items.toList())
        }
    }
}