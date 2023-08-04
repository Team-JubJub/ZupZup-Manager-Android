package com.example.zupzup_manager.data.service

import com.example.zupzup_manager.data.dto.merchandise.MerchandiseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface MerchandiseService {

    @GET("seller/{storeId}/management")
    suspend fun getMerchandiseList(
        @Header("accessToken") accessToken: String,
        @Path("storeId") storeId: Long
    ): Response<List<MerchandiseDto>>

//    @PATCH("seller/{storeId}/order/new-order/{orderId}/confirm")
//    suspend fun confirmOrder(
//        @Header("accessToken") accessToken: String,
//        @Path("storeId") storeId: Long,
//        @Path("orderId") orderId: Long,
//        @Body body: List<OrderSpecificDto>
//    ): Response<PatchOrderResponseDto>
}