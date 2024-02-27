package zupzup.manager.data.datasource.order

import zupzup.manager.data.dto.order.parameter.OrderSpecificListRequest
import zupzup.manager.data.dto.order.response.OrderListResponse
import zupzup.manager.data.dto.order.response.PatchOrderResponse
import retrofit2.Response

interface OrderDataSource {

    suspend fun getOrderList(storeId: Long): Response<OrderListResponse>

    suspend fun confirmOrder(storeId: Long, orderId: Long, orderList: OrderSpecificListRequest): Response<PatchOrderResponse>

    suspend fun rejectOrder(storeId: Long, orderId: Long): Response<PatchOrderResponse>

    suspend fun cancelOrder(storeId: Long, orderId: Long, orderList: OrderSpecificListRequest): Response<PatchOrderResponse>

    suspend fun completeOrder(storeId: Long, orderId: Long): Response<PatchOrderResponse>
}