package zupzup.manager.data.dto.store.parameter

data class ModifyStoreRequest(
    val openTime: String,
    val closeTime: String,
    val saleTimeStart: String,
    val saleTimeEnd: String,
    val closedDay: String
)