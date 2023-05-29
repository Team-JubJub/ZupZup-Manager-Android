package com.example.zupzup_manager.data.dto.mapper

import android.util.Log
import com.example.zupzup_manager.data.dto.CartDto
import com.example.zupzup_manager.data.dto.MerchandiseDto
import com.example.zupzup_manager.domain.models.CartModel
import com.example.zupzup_manager.domain.models.MerchandiseModel

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

    fun MerchandiseModel.toDto(): MerchandiseDto {
        return MerchandiseDto(
            itemId = itemId,
            storeId = storeId,
            itemName = itemName,
            price = price,
            discounted = discounted,
            imgUrl = imgUrl,
            stock = modifiedStock
        )
    }

}