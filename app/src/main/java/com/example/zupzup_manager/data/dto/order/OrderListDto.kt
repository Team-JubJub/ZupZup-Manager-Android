package com.example.zupzup_manager.data.dto.order

import com.example.zupzup_manager.domain.models.CustomerModel
import com.example.zupzup_manager.domain.models.OrderModel

data class OrderListDto(
    val orderList: List<OrderDto> = listOf()
)