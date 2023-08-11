package com.example.zupzup_manager.data.service

import com.example.zupzup_manager.data.dto.store.parameter.ModifyStoreRequest
import com.example.zupzup_manager.data.dto.store.response.ModifyStoreResponse
import com.example.zupzup_manager.data.dto.store.response.StoreDetailResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface StoreService {

    @GET("seller/{storeId}")
    suspend fun getStoreDetail(
        @Path("storeId") storeId: Long
    ): Response<StoreDetailResponse>

    @PATCH("seller/open/{storeId}")
    suspend fun changeOpenStatus(
        @Path("storeId") storeId: Long,
        @Query("isOpened") isOpened: Boolean
    ): Response<String>

    @Multipart
    @PATCH("seller/modification/{storeId}")
    suspend fun modifyStoreDetail(
        @Path("storeId") storeId: Long,
        @Part image: MultipartBody.Part?,
        @Part("data") data: ModifyStoreRequest
    ): Response<ModifyStoreResponse>

    @POST("seller/notice/{storeId}")
    suspend fun modifyStoreMatter(
        @Path("storeId") storeId: Long,
        @Query("storeMatters") storeMatter: String
    ): Response<String>
}