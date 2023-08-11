package com.example.zupzup_manager.data.dto.mapper

import com.example.zupzup_manager.data.dto.item.parameter.ItemPatchDto
import com.example.zupzup_manager.data.dto.item.parameter.ItemRequest
import com.example.zupzup_manager.data.dto.order.parameter.OrderSpecificDto
import com.example.zupzup_manager.data.dto.order.parameter.OrderSpecificListRequest
import com.example.zupzup_manager.data.dto.store.parameter.ModifyStoreRequest
import com.example.zupzup_manager.domain.models.item.ItemQuantityModel
import com.example.zupzup_manager.domain.models.item.ModifyItemModel
import com.example.zupzup_manager.domain.models.store.ModifyStoreModel
import com.example.zupzup_manager.domain.models.order.OrderSpecificModel

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