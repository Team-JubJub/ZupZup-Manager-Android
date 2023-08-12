package zupzup.manager.ui.orderdetail.models

data class OrderDetailHeaderModel(
    val orderId : Long,
    val storeId : Long,
    val orderStatus : String,
    val orderTitle : String,
    val orderTime: String,
    val storeName : String,
    val storeAddress: String,
    val category: String,
)
