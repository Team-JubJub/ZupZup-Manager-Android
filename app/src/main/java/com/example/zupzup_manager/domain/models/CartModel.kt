package com.example.zupzup_manager.domain.models

data class CartModel(
    val itemId: Long,
    val storeId: Long,
    val name: String,
    val salesPrice: Int,
    val amount: Int
)
