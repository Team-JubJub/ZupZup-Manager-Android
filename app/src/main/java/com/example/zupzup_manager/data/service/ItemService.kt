package com.example.zupzup_manager.data.service

import com.example.zupzup_manager.data.dto.item.parameter.ItemQuantityRequest
import com.example.zupzup_manager.data.dto.item.parameter.ItemRequest
import com.example.zupzup_manager.data.dto.item.response.ItemDto
import com.example.zupzup_manager.data.dto.store.parameter.ModifyStoreRequest
import com.example.zupzup_manager.data.dto.store.response.ModifyStoreResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.Part
import retrofit2.http.Path

interface ItemService {

    @GET("seller/{storeId}/management")
    suspend fun getItemList(
        @Path("storeId") storeId: Long
    ): Response<List<ItemDto>>

    @Multipart
    @PATCH("seller/{storeId}")
    suspend fun addItem(
        @Path("storeId") storeId: Long,
        @Part("item") item: ItemRequest,
        @Part image: MultipartBody.Part?
    ): Response<String>

    @Multipart
    @PATCH("seller/{storeId}/quantity")
    suspend fun modifyItemQuantity(
        @Path("storeId") storeId: Long,
        @Part("quantity") quantity: RequestBody
    ): Response<String>

    @Multipart
    @PATCH("seller/{storeId}/{itemId}")
    suspend fun modifyItem(
        @Path("storeId") storeId: Long,
        @Part("item") item: ItemRequest,
        @Part image: MultipartBody.Part?
    ): Response<String>

    @GET("seller/{storeId}/{itemId}")
    suspend fun deleteItem(
        @Path("storeId") storeId: Long,
        @Path("itemId") itemId: Long
    ): Response<String>
}