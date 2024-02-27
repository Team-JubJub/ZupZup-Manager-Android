package zupzup.manager.domain.usecase.order

import zupzup.manager.domain.DataResult
import zupzup.manager.domain.models.order.OrderModel
import zupzup.manager.domain.repository.OrderRepository
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
