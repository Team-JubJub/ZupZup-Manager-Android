package zupzup.manager.data.datasource.item

import zupzup.manager.data.dto.item.parameter.ItemPatchDto
import zupzup.manager.data.dto.item.parameter.ItemModifyRequest
import zupzup.manager.data.dto.item.response.ItemDto
import okhttp3.MultipartBody
import retrofit2.Response
import zupzup.manager.data.dto.item.parameter.ItemAddRequest

interface ItemDataSource {
    suspend fun getItemList(storeId: Long): Response<List<ItemDto>>
    suspend fun addItem(storeId: Long, item: ItemAddRequest, image: MultipartBody.Part?): Response<String>
    suspend fun modifyItemQuantity(storeId: Long, body: List<ItemPatchDto>): Response<String>
    suspend fun modifyItem(itemId: Long, storeId: Long, item: ItemModifyRequest, image: MultipartBody.Part?): Response<String>
    suspend fun deleteItem(storeId: Long, itemId: Long): Response<String>
}