package com.example.zupzup_manager.domain.usecase

import android.util.Log
import com.example.zupzup_manager.domain.DataResult
import com.example.zupzup_manager.domain.models.OrderModel
import com.example.zupzup_manager.domain.repository.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetOrderListUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke(accessToken: String, storeId: Long): Flow<DataResult<List<OrderModel>>> {
        return flow {
            orderRepository.getOrderList(accessToken, storeId).onSuccess { orderList ->
                Log.d("uc TAG", "orderlist test - success")
                emit(DataResult.Success(orderList))
            }.onFailure {
                Log.d("uc TAG", "orderlist test - fail")
                emit(DataResult.Failure("1"))
            }
        }.flowOn(Dispatchers.IO)
    }
}
