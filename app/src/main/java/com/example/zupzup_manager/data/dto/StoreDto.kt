package com.example.zupzup_manager.data.dto

data class StoreDto(
    val storeId: Long = 0,
    val name: String = "",
    val openTime: String = "",
    val hostPhoneNumber: String = "",
    val longitude: Double = 0.0,
    val latitude: Double = 0.0,
    val address: String = "",
    val eventList: List<String> = listOf(),
    val merchandiseList: List<MerchandiseDto> = listOf(),
    val saleTimeStart: Int = 0,
    val saleTimeEnd: Int = 0
)