package zupzup.manager.data.dto.mapper

import zupzup.manager.data.dto.item.parameter.ItemPatchDto
import zupzup.manager.data.dto.item.parameter.ItemRequest
import zupzup.manager.data.dto.order.parameter.OrderSpecificDto
import zupzup.manager.data.dto.order.parameter.OrderSpecificListRequest
import zupzup.manager.data.dto.store.parameter.ModifyStoreRequest
import zupzup.manager.domain.models.item.ItemQuantityModel
import zupzup.manager.domain.models.item.ModifyItemModel
import zupzup.manager.domain.models.store.ModifyStoreModel
import zupzup.manager.domain.models.order.OrderSpecificModel

object DtoMapper {

    fun List<OrderSpecificModel>.toDto(): OrderSpecificListRequest {
        val orderSpecificDtoList = this.map {
            OrderSpecificDto(
                itemId = it.itemId,
                itemName = it.itemName,
                itemPrice = it.itemPrice,
                itemCount = it.itemCount
            )
        }
        return OrderSpecificListRequest(orderSpecificDtoList)
    }

    fun ModifyItemModel.toDto(): ItemRequest {
        return ItemRequest(
            itemName = itemName,
            imageURL = imageURL,
            itemPrice = itemPrice,
            salePrice = salePrice,
            itemCount = itemCount
        )
    }

    fun List<ItemQuantityModel>.toDto(): List<ItemPatchDto> {
        val itemPatchDtoList = this.map {
            ItemPatchDto(
                itemId = it.itemId,
                itemCount = it.itemCount
            )
        }
        return itemPatchDtoList
    }

    fun ModifyStoreModel.toDto(): ModifyStoreRequest {
        return ModifyStoreRequest(
            openTime = openTime,
            closeTime = closeTime,
            saleTimeStart = saleTimeStart,
            saleTimeEnd = saleTimeEnd,
            closedDay = closedDay!!
        )
    }

}