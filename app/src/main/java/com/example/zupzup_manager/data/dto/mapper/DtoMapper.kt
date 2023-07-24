package com.example.zupzup_manager.data.dto.mapper

import com.example.zupzup_manager.data.dto.order.OrderSpecificDto
import com.example.zupzup_manager.data.dto.merchandise.MerchandiseDto
import com.example.zupzup_manager.domain.models.OrderSpecificModel
import com.example.zupzup_manager.domain.models.MerchandiseModel

object DtoMapper {

    fun OrderSpecificModel.toDto(): OrderSpecificDto {
        return OrderSpecificDto(
            itemId = itemId,
            itemName = itemName,
            itemPrice = itemPrice,
            itemCount = itemCount
        )
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

}