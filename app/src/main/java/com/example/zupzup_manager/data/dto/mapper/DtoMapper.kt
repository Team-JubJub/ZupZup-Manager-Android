package com.example.zupzup_manager.data.dto.mapper

import com.example.zupzup_manager.data.dto.order.OrderSpecificDto
import com.example.zupzup_manager.data.dto.merchandise.MerchandiseDto
import com.example.zupzup_manager.data.dto.order.OrderSpecificListDto
import com.example.zupzup_manager.data.dto.store.parameter.ModifyStoreRequestBody
import com.example.zupzup_manager.domain.models.OrderSpecificModel
import com.example.zupzup_manager.domain.models.MerchandiseModel
import com.example.zupzup_manager.domain.models.ModifyStoreModel

object DtoMapper {

    fun List<OrderSpecificModel>.toDto(): OrderSpecificListDto {
        val orderSpecificDtoList = this.map {
            OrderSpecificDto(
                itemId = it.itemId,
                itemName = it.itemName,
                itemPrice = it.itemPrice,
                itemCount = it.itemCount
            )
        }
        return OrderSpecificListDto(orderSpecificDtoList)
    }

    fun MerchandiseModel.toDto(): MerchandiseDto {
        return MerchandiseDto(
            itemId = itemId,
            itemName = itemName,
            imageUrl = imageUrl,
            itemPrice = itemPrice,
            salePrice = salePrice,
            itemCount = itemCount
        )
    }

    fun ModifyStoreModel.toDto(): ModifyStoreRequestBody {
        return ModifyStoreRequestBody(
            openTime = openTime,
            closeTime = closeTime,
            saleTimeStart = saleTimeStart,
            saleTimeEnd = saleTimeEnd,
            closedDay = closedDay!!
        )
    }

}