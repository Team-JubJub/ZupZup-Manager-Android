package zupzup.manager.data.dto.item.parameter

data class ItemAddRequest(
    val itemName: String,
    val itemPrice: Int,
    val salePrice: Int,
    var itemCount: Int
)