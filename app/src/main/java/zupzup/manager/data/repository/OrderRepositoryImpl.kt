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
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    val orderList = responseBody.orders.map { dto -> dto.toOrderModel() }
                    Result.success(orderList)
                } else {
                    // 서버가 204 응답을 보낸 경우, 빈 리스트 반환
                    Result.success(emptyList())
                }
            } else {
                // 서버에서 에러 응답을 받은 경우
                Result.failure(Exception("서버에서 오류 응답을 받았습니다."))
            }
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