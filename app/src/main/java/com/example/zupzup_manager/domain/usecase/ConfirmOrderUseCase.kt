package com.example.zupzup_manager.domain.usecase

import com.example.zupzup_manager.domain.models.OrderSpecificModel
import com.example.zupzup_manager.domain.repository.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ConfirmOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke(accessToken: String, storeId: Long, orderId: Long, orderList: List<OrderSpecificModel>): Flow<Result<Int>> {
        return flow {
            emit(orderRepository.confirmOrder(accessToken, storeId, orderId, orderList))
        }.flowOn(Dispatchers.IO)
    }
}