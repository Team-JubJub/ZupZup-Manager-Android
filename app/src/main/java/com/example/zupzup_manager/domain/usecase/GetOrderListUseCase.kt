package com.example.zupzup_manager.domain.usecase

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
    suspend operator fun invoke(storeId: Long): Flow<DataResult<List<OrderModel>>> {
        return flow {
            orderRepository.getOrderList(storeId).onSuccess { orderList ->
                emit(DataResult.Success(orderList))
            }.onFailure {
                emit(DataResult.Failure("1"))
            }
        }.flowOn(Dispatchers.IO)
    }
}
