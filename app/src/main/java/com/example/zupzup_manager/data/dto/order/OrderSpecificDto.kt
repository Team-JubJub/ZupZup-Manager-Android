package com.example.zupzup_manager.data.dto.order

import com.example.zupzup_manager.domain.models.OrderSpecificModel

data class OrderSpecificDto(
    val itemId: Long = 0,
    val itemName: String = "",
    val itemPrice: Int = 0,
    val itemCount: Int = 0
) {
    fun toOrderSpecificModel(): OrderSpecificModel {
        return OrderSpecificModel(
            itemId = itemId,
            itemName = itemName,
            itemPrice = itemPrice,
            itemCount = itemCount
        )
    }
}
