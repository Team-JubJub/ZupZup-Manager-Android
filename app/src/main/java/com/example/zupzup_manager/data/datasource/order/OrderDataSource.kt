package com.example.zupzup_manager.data.datasource.order

import com.example.zupzup_manager.data.dto.order.OrderDto
import com.example.zupzup_manager.data.dto.order.OrderListDto
import com.example.zupzup_manager.data.dto.order.OrderSpecificDto
import com.example.zupzup_manager.data.dto.order.OrderSpecificListDto
import com.example.zupzup_manager.data.dto.order.PatchOrderResponseDto
import com.google.android.gms.tasks.Task
import retrofit2.Response

interface OrderDataSource {

    suspend fun getOrderList(accessToken: String, storeId: Long): Response<OrderListDto>

    suspend fun confirmOrder(accessToken: String, storeId: Long, orderId: Long, orderList: OrderSpecificListDto): Response<PatchOrderResponseDto>

    suspend fun rejectOrder(accessToken: String, storeId: Long, orderId: Long): Response<PatchOrderResponseDto>

    suspend fun cancelOrder(accessToken: String, storeId: Long, orderId: Long): Response<PatchOrderResponseDto>

    suspend fun completeOrder(accessToken: String, storeId: Long, orderId: Long): Response<PatchOrderResponseDto>
}