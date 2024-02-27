package zupzup.manager.data.dto.order.parameter

import zupzup.manager.domain.models.order.OrderSpecificModel

data class OrderSpecificDto(
    val itemId: Long = 0,
    val itemName: String = "",
    val imageUrl: String = "",
    val itemPrice: Int = 0,
    val salePrice: Int = 0,
    val itemCount: Int = 0
) {
    fun toOrderSpecificModel(): OrderSpecificModel {
        return OrderSpecificModel(
            itemId = itemId,
            itemName = itemName,
            imageUrl = imageUrl,
            itemPrice = itemPrice,
            salePrice = salePrice,
            itemCount = itemCount
        )
    }
}
