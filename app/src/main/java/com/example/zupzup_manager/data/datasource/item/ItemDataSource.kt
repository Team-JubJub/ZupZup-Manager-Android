package com.example.zupzup_manager.data.datasource.item

import com.example.zupzup_manager.data.dto.item.parameter.ItemPatchDto
import com.example.zupzup_manager.data.dto.item.parameter.ItemRequest
import com.example.zupzup_manager.data.dto.item.response.ItemDto
import okhttp3.MultipartBody
import retrofit2.Response

interface ItemDataSource {
    suspend fun getItemList(storeId: Long): Response<List<ItemDto>>
    suspend fun addItem(storeId: Long, item: ItemRequest, image: MultipartBody.Part?): Response<String>
    suspend fun modifyItemQuantity(storeId: Long, body: List<ItemPatchDto>): Response<String>
    suspend fun modifyItem(storeId: Long, item: ItemRequest, image: MultipartBody.Part?): Response<String>
    suspend fun deleteItem(storeId: Long, itemId: Long): Response<String>
}