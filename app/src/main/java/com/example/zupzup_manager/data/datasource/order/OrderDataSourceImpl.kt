package com.example.zupzup_manager.data.datasource.order

import com.example.zupzup_manager.data.dto.order.parameter.OrderSpecificListRequest
import com.example.zupzup_manager.data.dto.order.response.OrderListResponse
import com.example.zupzup_manager.data.dto.order.response.PatchOrderResponse
import com.example.zupzup_manager.data.service.OrderService
import retrofit2.Response
import javax.inject.Inject

class OrderDataSourceImpl @Inject constructor(
    private val orderService: OrderService
) : OrderDataSource {
    override suspend fun getOrderList(storeId: Long): Response<OrderListResponse> {
        return orderService.getOrderList(
            storeId = storeId
        )
    }

    override suspend fun confirmOrder(storeId: Long, orderId: Long, orderList: OrderSpecificListRequest): Response<PatchOrderResponse> {
        return orderService.confirmOrder(
            storeId = storeId,
            orderId = orderId,
            body = orderList
        )
    }

    override suspend fun rejectOrder(storeId: Long, orderId: Long): Response<PatchOrderResponse> {
        return orderService.rejectOrder(
            storeId = storeId,
            orderId = orderId
        )
    }

    override suspend fun cancelOrder(storeId: Long, orderId: Long): Response<PatchOrderResponse> {
        return orderService.cancelOrder(
            storeId = storeId,
            orderId = orderId
        )
    }

    override suspend fun completeOrder(storeId: Long, orderId: Long): Response<PatchOrderResponse> {
        return orderService.completeOrder(
            storeId = storeId,
            orderId = orderId
        )
    }
}