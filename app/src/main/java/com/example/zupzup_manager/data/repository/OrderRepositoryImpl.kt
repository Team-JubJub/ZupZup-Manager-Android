package com.example.zupzup_manager.data.repository

import android.util.Log
import com.example.zupzup_manager.data.datasource.order.OrderDataSource
import com.example.zupzup_manager.data.dto.mapper.DtoMapper.toDto
import com.example.zupzup_manager.di.NetworkModule
import com.example.zupzup_manager.domain.models.OrderModel
import com.example.zupzup_manager.domain.models.OrderSpecificModel
import com.example.zupzup_manager.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Retrofit
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderDataSource: OrderDataSource
) : OrderRepository {

    override suspend fun getOrderList(
        accessToken: String,
        storeId: Long
    ): Result<List<OrderModel>> {
        return try{
            val response = orderDataSource.getOrderList(accessToken, storeId)
            var orderList = listOf<OrderModel>()
            if (response.isSuccessful){
                orderList = response.body()!!.orderList.map { dto -> dto.toOrderModel()}
            }
            Result.success(orderList)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun confirmOrder(
        accessToken: String,
        storeId: Long,
        orderId: Long,
        orderList: List<OrderSpecificModel>
    ): Result<Int> {
        return try {
            orderDataSource.confirmOrder(accessToken, storeId, orderId, orderList.toDto())
            Result.success(0)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun rejectOrder(
        accessToken: String,
        storeId: Long,
        orderId: Long
    ): Result<Int> {
        return try {
            orderDataSource.rejectOrder(accessToken, storeId, orderId)
            Result.success(0)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun cancelOrder(
        accessToken: String,
        storeId: Long,
        orderId: Long
    ): Result<Int> {
        return try {
            orderDataSource.cancelOrder(accessToken, storeId, orderId)
            Result.success(0)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun completeOrder(
        accessToken: String,
        storeId: Long,
        orderId: Long
    ): Result<Int> {
        return try {
            orderDataSource.completeOrder(accessToken, storeId, orderId)
            Result.success(0)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}