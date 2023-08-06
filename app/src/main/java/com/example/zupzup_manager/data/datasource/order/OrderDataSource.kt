package com.example.zupzup_manager.data.datasource.order

import com.example.zupzup_manager.data.dto.order.OrderListDto
import com.example.zupzup_manager.data.dto.order.OrderSpecificListDto
import com.example.zupzup_manager.data.dto.order.PatchOrderResponseDto
import retrofit2.Response

interface OrderDataSource {

    suspend fun getOrderList(storeId: Long): Response<OrderListDto>

    suspend fun confirmOrder(storeId: Long, orderId: Long, orderList: OrderSpecificListDto): Response<PatchOrderResponseDto>

    suspend fun rejectOrder(storeId: Long, orderId: Long): Response<PatchOrderResponseDto>

    suspend fun cancelOrder(storeId: Long, orderId: Long): Response<PatchOrderResponseDto>

    suspend fun completeOrder(storeId: Long, orderId: Long): Response<PatchOrderResponseDto>
}