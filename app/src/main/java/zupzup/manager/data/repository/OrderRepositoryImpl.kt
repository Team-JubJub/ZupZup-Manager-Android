package zupzup.manager.data.repository

import zupzup.manager.data.datasource.order.OrderDataSource
import zupzup.manager.data.dto.mapper.DtoMapper.toDto
import zupzup.manager.domain.models.order.OrderModel
import zupzup.manager.domain.models.order.OrderSpecificModel
import zupzup.manager.domain.repository.OrderRepository
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val orderDataSource: OrderDataSource
) : OrderRepository {

    override suspend fun getOrderList(
        storeId: Long
    ): Result<List<OrderModel>> {
        return try{
            val response = orderDataSource.getOrderList(storeId)
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
        storeId: Long,
        orderId: Long,
        orderList: List<OrderSpecificModel>
    ): Result<Int> {
        return try {
            orderDataSource.confirmOrder(storeId, orderId, orderList.toDto())
            Result.success(0)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun rejectOrder(
        storeId: Long,
        orderId: Long
    ): Result<Int> {
        return try {
            orderDataSource.rejectOrder(storeId, orderId)
            Result.success(0)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun cancelOrder(
        storeId: Long,
        orderId: Long
    ): Result<Int> {
        return try {
            orderDataSource.cancelOrder(storeId, orderId)
            Result.success(0)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun completeOrder(
        storeId: Long,
        orderId: Long
    ): Result<Int> {
        return try {
            orderDataSource.completeOrder(storeId, orderId)
            Result.success(0)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}