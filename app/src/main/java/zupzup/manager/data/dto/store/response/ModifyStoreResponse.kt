package zupzup.manager.data.dto.store.response

import zupzup.manager.domain.models.store.StoreModel

data class ModifyStoreResponse(
    val storeName: String = "",
    val openTime: String = "",
    val closeTime: String = "",
    val saleTimeStart: String = "",
    val saleTimeEnd: String = "",
    val saleMatters: String = "",
    val closedDay: String? = ""
) {
    fun toStoreModel(): StoreModel {
        return StoreModel(
            storeName = storeName,
            openTime = openTime,
            closeTime = closeTime,
            saleTimeStart = saleTimeStart,
            saleTimeEnd = saleTimeEnd,
            saleMatters = saleMatters,
            closedDay = closedDay
        )
    }
}