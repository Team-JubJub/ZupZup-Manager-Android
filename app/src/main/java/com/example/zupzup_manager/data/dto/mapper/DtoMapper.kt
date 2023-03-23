package com.example.zupzup_manager.data.dto.mapper

import com.example.zupzup_manager.data.dto.CartDto
import com.example.zupzup_manager.domain.models.CartModel

object DtoMapper {

    fun CartModel.toDto(): CartDto {
        return CartDto(
            itemId = itemId,
            storeId = storeId,
            name = name,
            salesPrice = salesPrice,
            amount = amount
        )
    }

}