package com.example.zupzup_manager.data.dto

import com.example.zupzup_manager.domain.models.MerchandiseModel

data class MerchandiseDto(
    val itemId: Long = 0,
    val storeId: Long = 0,
    val itemName: String = "",
    val price: Int = 0,
    val discounted: Int = 0,
    val imgUrl: String = "",
    var stock: Int = 0
) {
    fun toMerchandiseModel(): MerchandiseModel {
        return MerchandiseModel(
            itemId = itemId,
            storeId = storeId,
            itemName = itemName,
            price = price,
            discounted = discounted,
            imgUrl = imgUrl,
            stock = stock
        )
    }
}
