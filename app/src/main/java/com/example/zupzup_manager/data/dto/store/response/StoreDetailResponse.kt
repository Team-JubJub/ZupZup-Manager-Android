package com.example.zupzup_manager.data.dto.store.response

import com.example.zupzup_manager.domain.models.store.StoreModel

data class StoreDetailResponse(
    val storeId: Long = 0,
    val sellerId: Long = 0,
    val storeName: String = "",
    val storeImageUrl: String = "",
    val storeAddress: String = "",
    val category: String = "",
    val contact: String = "",
    val longitude: Double = 0.0,
    val latitude: Double = 0.0,
    val openTime: String = "",
    val closeTime: String = "",
    val saleTimeStart: String = "",
    val saleTimeEnd: String = "",
    val saleMatters: String = "",
    val isOpen: Boolean = false,
    val closedDay: String? = ""
) {
    fun toStoreModel(): StoreModel {
        return StoreModel(
            storeId = storeId,
            sellerId = sellerId,
            storeName = storeName,
            storeImageUrl = storeImageUrl,
            storeAddress = storeAddress,
            category = category,
            contact = contact,
            longitude = longitude,
            latitude = latitude,
            openTime = openTime,
            closeTime = closeTime,
            saleTimeStart = saleTimeStart,
            saleTimeEnd = saleTimeEnd,
            saleMatters = saleMatters,
            isOpen = isOpen,
            closedDay = closedDay
        )
    }
}