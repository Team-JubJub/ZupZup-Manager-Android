package com.example.zupzup_manager.data.dto

import com.example.zupzup_manager.domain.models.StoreModel

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
) {
    fun toStoreModel(): StoreModel {
        return StoreModel(
            storeId = storeId,
            name = name,
            openTime = openTime,
            hostPhoneNumber = hostPhoneNumber,
            location = Pair(latitude, longitude),
            address = address,
            eventList = eventList,
            merchandiseList = merchandiseList.map { it.toMerchandiseModel() },
            saleTime = Pair(saleTimeStart, saleTimeEnd)
        )
    }
}