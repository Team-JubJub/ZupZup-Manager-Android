package zupzup.manager.data.dto.item.parameter

data class ItemModifyRequest(
    val itemName: String,
    val imageURL: String,
    val itemPrice: Int,
    val salePrice: Int,
    var itemCount: Int
)