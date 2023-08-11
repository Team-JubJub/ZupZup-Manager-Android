package com.example.zupzup_manager.data.dto.item.parameter

data class ItemRequest(
    val itemName: String,
    val imageURL: String,
    val itemPrice: Int,
    val salePrice: Int,
    var itemCount: Int
)