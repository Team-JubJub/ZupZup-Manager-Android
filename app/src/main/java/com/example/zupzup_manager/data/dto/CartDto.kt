package com.example.zupzup_manager.data.dto

import com.example.zupzup_manager.domain.models.CartModel

data class CartDto(
    val itemId: Long = 0,
    val storeId: Long = 0,
    val name: String = "",
    val salesPrice: Int = 0,
    val amount: Int = 0
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
