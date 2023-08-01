package com.example.zupzup_manager.data.dto.order

import com.example.zupzup_manager.domain.models.CustomerModel
import com.example.zupzup_manager.domain.models.OrderModel

data class OrderSpecificListDto(
    val orderList: List<OrderSpecificDto> = listOf()
)