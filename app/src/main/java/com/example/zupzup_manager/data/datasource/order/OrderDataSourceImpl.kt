package com.example.zupzup_manager.data.datasource.order

import com.example.zupzup_manager.data.dto.order.OrderListDto
import com.example.zupzup_manager.data.dto.order.OrderSpecificListDto
import com.example.zupzup_manager.data.dto.order.PatchOrderResponseDto
import com.example.zupzup_manager.data.service.OrderService
import retrofit2.Response
import javax.inject.Inject

class OrderDataSourceImpl @Inject constructor(
    private val orderService: OrderService
) : OrderDataSource {
    override suspend fun getOrderList(storeId: Long): Response<OrderListDto> {
        return orderService.getOrderList(
            storeId = storeId
        )
    }

    override suspend fun confirmOrder(storeId: Long, orderId: Long, orderList: OrderSpecificListDto): Response<PatchOrderResponseDto> {
        return orderService.confirmOrder(
            storeId = storeId,
            orderId = orderId,
            body = orderList
        )
    }

    override suspend fun rejectOrder(storeId: Long, orderId: Long): Response<PatchOrderResponseDto> {
        return orderService.rejectOrder(
            storeId = storeId,
            orderId = orderId
        )
    }

    override suspend fun cancelOrder(storeId: Long, orderId: Long): Response<PatchOrderResponseDto> {
        return orderService.cancelOrder(
            storeId = storeId,
            orderId = orderId
        )
    }

    override suspend fun completeOrder(storeId: Long, orderId: Long): Response<PatchOrderResponseDto> {
        return orderService.completeOrder(
            storeId = storeId,
            orderId = orderId
        )
    }
}