package com.example.zupzup_manager.data.dto

import com.example.zupzup_manager.domain.models.CartModel

data class CartDto(
    val itemId: Long,
    val storeId: Long,
    val name: String,
    val salesPrice: Int,
    val amount: Int
) {
    fun toCartModel(): CartModel {
        return CartModel(
            itemId = itemId,
            storeId = storeId,
            name = name,
            salesPrice = salesPrice,
            amount = amount
        )
    }
}
