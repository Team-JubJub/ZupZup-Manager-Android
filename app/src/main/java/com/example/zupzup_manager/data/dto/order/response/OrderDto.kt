package com.example.zupzup_manager.data.dto.order.response

import com.example.zupzup_manager.data.dto.order.parameter.OrderSpecificDto
import com.example.zupzup_manager.domain.models.order.CustomerModel
import com.example.zupzup_manager.domain.models.order.OrderModel

data class OrderDto(
    val orderId: Long = 0,
    val storeId: Long = 0,
    val userId: Long = 0,
    val orderStatus: String = "",
    val userName: String = "",
    val phoneNumber: String = "",
    val orderTitle: String = "",
    val orderTime: String = "",
    val visitTime: String = "",
    val storeName: String = "",
    val storeAddress: String = "",
    val category: String = "",
    val orderList: List<OrderSpecificDto> = listOf()
) {
    fun toOrderModel(): OrderModel {
        return OrderModel(
            orderId = orderId,
            storeId = storeId,
            customer = CustomerModel(userId, userName, phoneNumber),
            orderStatus = orderStatus,
            orderTitle = orderTitle,
            orderTime = orderTime,
            visitTime = visitTime,
            storeName = storeName,
            storeAddress = storeAddress,
            category = category,
            orderList = orderList.map { it.toOrderSpecificModel() },
        )
    }
}