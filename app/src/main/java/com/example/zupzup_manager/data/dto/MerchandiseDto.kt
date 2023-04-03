package com.example.zupzup_manager.data.dto

data class MerchandiseDto(
    val itemId: Long = 0,
    val storeId: Long = 0,
    val itemName: String = "",
    val price: Int = 0,
    val discounted: Int = 0,
    val imgUrl: String = "",
    var stock: Int = 0
)
