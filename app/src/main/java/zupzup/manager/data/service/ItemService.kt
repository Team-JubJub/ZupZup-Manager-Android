package zupzup.manager.data.service

import zupzup.manager.data.dto.item.parameter.ItemModifyRequest
import zupzup.manager.data.dto.item.response.ItemDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import zupzup.manager.data.dto.item.parameter.ItemAddRequest

interface ItemService {

    @GET("seller/{storeId}/management")
    suspend fun getItemList(
        @Path("storeId") storeId: Long
    ): Response<List<ItemDto>>

    @Multipart
    @POST("seller/{storeId}")
    suspend fun addItem(
        @Path("storeId") storeId: Long,
        @Part("item") item: ItemAddRequest,
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
        @Part("item") item: ItemModifyRequest,
        @Part image: MultipartBody.Part?
    ): Response<String>

    @DELETE("seller/{storeId}/{itemId}")
    suspend fun deleteItem(
        @Path("storeId") storeId: Long,
        @Path("itemId") itemId: Long
    ): Response<String>
}