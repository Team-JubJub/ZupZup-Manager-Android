package zupzup.manager.data.dto.store.response

import zupzup.manager.domain.models.store.StoreModel

data class StoreDetailResponse(
    val storeId: Long = 0,
    val sellerId: Long = 0,
    val storeName: String = "",
    val storeImageUrl: String = "",
    val storeAddress: String = "",
    val category: String = "",
    val sellerName: String = "",
    val sellerContact: String = "",
    val storeContact: String = "",
    val longitude: Double = 0.0,
    val latitude: Double = 0.0,
    val openTime: String = "",
    val closeTime: String = "",
    val saleTimeStart: String = "",
    val saleTimeEnd: String = "",
    val saleMatters: String = "",
    val promotion: String = "",
    val isOpen: Boolean = false,
    val closedDay: String? = "",
    val starredUsers: List<Long> = listOf(),
    val crNumber: String = ""
) {
    fun toStoreModel(): StoreModel {
        return StoreModel(
            storeId = storeId,
            sellerId = sellerId,
            storeName = storeName,
            storeImageUrl = storeImageUrl,
            storeAddress = storeAddress,
            category = category,
            sellerName = sellerName,
            sellerContact = sellerContact,
            storeContact = storeContact,
            longitude = longitude,
            latitude = latitude,
            openTime = openTime,
            closeTime = closeTime,
            saleTimeStart = saleTimeStart,
            saleTimeEnd = saleTimeEnd,
            saleMatters = saleMatters,
            isOpen = isOpen,
            closedDay = closedDay,
            starredUsers = starredUsers,
            crNumber = crNumber
        )
    }
}