package com.example.zupzup_manager.domain.repository

import com.example.zupzup_manager.domain.models.OrderModel
import com.example.zupzup_manager.domain.models.OrderSpecificModel

interface OrderRepository {
    suspend fun getOrderList(storeId: Long): Result<List<OrderModel>>

    suspend fun confirmOrder(storeId: Long, orderId: Long, orderList: List<OrderSpecificModel>): Result<Int>

    suspend fun rejectOrder(storeId: Long, orderId: Long) : Result<Int>

    suspend fun cancelOrder(storeId: Long, orderId: Long) : Result<Int>

    suspend fun completeOrder(storeId: Long, orderId: Long) : Result<Int>
}