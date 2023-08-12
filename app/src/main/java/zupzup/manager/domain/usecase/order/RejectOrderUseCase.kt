package zupzup.manager.domain.usecase.order

import zupzup.manager.domain.repository.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RejectOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke(storeId: Long, orderId: Long): Flow<Result<Int>> {
        return flow {
            emit(orderRepository.rejectOrder(storeId, orderId))
        }.flowOn(Dispatchers.IO)
    }
}