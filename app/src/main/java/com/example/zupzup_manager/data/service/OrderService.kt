package com.example.zupzup_manager.data.service

import com.example.zupzup_manager.data.dto.order.OrderListDto
import com.example.zupzup_manager.data.dto.order.OrderSpecificListDto
import com.example.zupzup_manager.data.dto.order.PatchOrderResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface OrderService {

    @GET("seller/{storeId}/order")
    suspend fun getOrderList(
        @Path("storeId") storeId: Long
    ): Response<OrderListDto>

    @PATCH("seller/{storeId}/order/new-order/{orderId}/confirm")
    suspend fun confirmOrder(
        @Path("storeId") storeId: Long,
        @Path("orderId") orderId: Long,
        @Body body: OrderSpecificListDto
    ): Response<PatchOrderResponseDto>

    @PATCH("seller/{storeId}/order/new-order/{orderId}/cancel")
    suspend fun rejectOrder(
        @Path("storeId") storeId: Long,
        @Path("orderId") orderId: Long
    ): Response<PatchOrderResponseDto>

    @PATCH("seller/{storeId}/order/confirmed-order/{orderId}/complete")
    suspend fun cancelOrder(
        @Path("storeId") storeId: Long,
        @Path("orderId") orderId: Long
    ): Response<PatchOrderResponseDto>

    @PATCH("seller/{storeId}/order/confirmed-order/{orderId}/cancel")
    suspend fun completeOrder(
        @Path("storeId") storeId: Long,
        @Path("orderId") orderId: Long
    ): Response<PatchOrderResponseDto>
}