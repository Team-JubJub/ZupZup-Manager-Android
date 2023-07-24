package com.example.zupzup_manager.data.datasource.order

import com.example.zupzup_manager.data.dto.order.OrderDto
import com.example.zupzup_manager.data.dto.order.OrderListDto
import com.example.zupzup_manager.data.dto.order.OrderSpecificDto
import com.example.zupzup_manager.data.dto.order.PatchOrderResponseDto
import com.example.zupzup_manager.data.service.OrderService
import com.google.android.gms.tasks.Task
import retrofit2.Response
import javax.inject.Inject

class OrderDataSourceImpl @Inject constructor(
    private val orderService: OrderService
) : OrderDataSource {
    override suspend fun getOrderList(accessToken: String, storeId: Long): Response<OrderListDto> {
        return orderService.getOrderList(
            storeId = storeId,
            accessToken = accessToken
        )
    }

    override suspend fun confirmOrder(accessToken: String, storeId: Long, orderId: Long, orderList: List<OrderSpecificDto>): Response<PatchOrderResponseDto> {
        return orderService.confirmOrder(
            accessToken = accessToken,
            storeId = storeId,
            orderId = orderId,
            body = orderList
        )
    }

    override suspend fun rejectOrder(accessToken: String, storeId: Long, orderId: Long): Response<PatchOrderResponseDto> {
        return orderService.rejectOrder(
            accessToken = accessToken,
            storeId = storeId,
            orderId = orderId
        )
    }

    override suspend fun cancelOrder(accessToken: String, storeId: Long, orderId: Long): Response<PatchOrderResponseDto> {
        return orderService.cancelOrder(
            accessToken = accessToken,
            storeId = storeId,
            orderId = orderId
        )
    }

    override suspend fun completeOrder(accessToken: String, storeId: Long, orderId: Long): Response<PatchOrderResponseDto> {
        return orderService.completeOrder(
            accessToken = accessToken,
            storeId = storeId,
            orderId = orderId
        )
    }
}