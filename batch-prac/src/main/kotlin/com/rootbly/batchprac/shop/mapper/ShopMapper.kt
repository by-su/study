package com.rootbly.batchprac.shop.mapper

import com.rootbly.batchprac.shop.Shop
import com.rootbly.batchprac.shop.ShopRankSnapShot
import org.apache.ibatis.annotations.Mapper

@Mapper
interface ShopMapper {
    fun findAllOrderByTotalSalePriceDesc(): List<Shop>
    fun bulkUpsertShopRankSnapshot(shopRankSnapshots: List<ShopRankSnapShot>)
}