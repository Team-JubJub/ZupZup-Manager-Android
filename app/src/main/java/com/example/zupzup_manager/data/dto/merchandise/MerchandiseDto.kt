package com.example.zupzup_manager.data.dto.merchandise

import com.example.zupzup_manager.domain.models.MerchandiseModel

data class MerchandiseDto(
    val itemId: Long = 0,
    val itemName: String = "",
    val imageUrl: String = "",
    val itemPrice: Int = 0,
    val salePrice: Int = 0,
    var itemCount: Int = 0
) {
    fun toMerchandiseModel(): MerchandiseModel {
        return MerchandiseModel(
            itemId = itemId,
            itemName = itemName,
            imageUrl = imageUrl,
            itemPrice = itemPrice,
            salePrice = salePrice,
            itemCount = itemCount
        )
    }
}
