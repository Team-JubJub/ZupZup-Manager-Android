package com.example.zupzup_manager.domain.repository

import com.example.zupzup_manager.domain.models.OrderSpecificModel
import com.example.zupzup_manager.domain.models.OrderModel
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    suspend fun getOrderList(accessToken: String, storeId: Long): Result<List<OrderModel>>

    suspend fun confirmOrder(accessToken: String, storeId: Long, orderId: Long, orderList: List<OrderSpecificModel>): Result<Int>

    suspend fun rejectOrder(accessToken: String, storeId: Long, orderId: Long) : Result<Int>

    suspend fun cancelOrder(accessToken: String, storeId: Long, orderId: Long) : Result<Int>

    suspend fun completeOrder(accessToken: String, storeId: Long, orderId: Long) : Result<Int>
}