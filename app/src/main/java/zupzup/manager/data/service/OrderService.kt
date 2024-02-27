package zupzup.manager.data.service

import zupzup.manager.data.dto.order.response.OrderListResponse
import zupzup.manager.data.dto.order.parameter.OrderSpecificListRequest
import zupzup.manager.data.dto.order.response.PatchOrderResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface OrderService {

    @GET("seller/{storeId}/order")
    suspend fun getOrderList(
        @Path("storeId") storeId: Long
    ): Response<OrderListResponse>

    @PATCH("seller/{storeId}/order/new-order/{orderId}/confirm")
    suspend fun confirmOrder(
        @Path("storeId") storeId: Long,
        @Path("orderId") orderId: Long,
        @Body body: OrderSpecificListRequest
    ): Response<PatchOrderResponse>

    @PATCH("seller/{storeId}/order/new-order/{orderId}/cancel")
    suspend fun rejectOrder(
        @Path("storeId") storeId: Long,
        @Path("orderId") orderId: Long
    ): Response<PatchOrderResponse>

    @PATCH("seller/{storeId}/order/confirmed-order/{orderId}/cancel")
    suspend fun cancelOrder(
        @Path("storeId") storeId: Long,
        @Path("orderId") orderId: Long,
        @Body body: OrderSpecificListRequest
    ): Response<PatchOrderResponse>

    @PATCH("seller/{storeId}/order/confirmed-order/{orderId}/complete")
    suspend fun completeOrder(
        @Path("storeId") storeId: Long,
        @Path("orderId") orderId: Long
    ): Response<PatchOrderResponse>
}