package zupzup.manager.data.dto.item.response

import zupzup.manager.domain.models.item.ItemModel

data class ItemDto(
    val itemId: Long = 0,
    val itemName: String = "",
    val imageURL: String = "",
    val itemPrice: Int = 0,
    val salePrice: Int = 0,
    var itemCount: Int = 0
) {
    fun toItemModel(): ItemModel {
        return ItemModel(
            itemId = itemId,
            itemName = itemName,
            imageURL = imageURL,
            itemPrice = itemPrice,
            salePrice = salePrice,
            itemCount = itemCount
        )
    }
}
