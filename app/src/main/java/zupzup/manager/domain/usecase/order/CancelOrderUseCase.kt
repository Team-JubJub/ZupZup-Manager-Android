package zupzup.manager.domain.usecase.order

import zupzup.manager.domain.repository.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import zupzup.manager.domain.models.order.OrderSpecificModel
import javax.inject.Inject

class CancelOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository
) {
    suspend operator fun invoke(storeId: Long, orderId: Long, orderList: List<OrderSpecificModel>): Flow<Result<Int>> {
        return flow {
            emit(orderRepository.cancelOrder(storeId, orderId, orderList))
        }.flowOn(Dispatchers.IO)
    }
}