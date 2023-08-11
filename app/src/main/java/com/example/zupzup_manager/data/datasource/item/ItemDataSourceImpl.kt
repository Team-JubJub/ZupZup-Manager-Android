package com.example.zupzup_manager.data.datasource.item

import com.example.zupzup_manager.data.dto.item.parameter.ItemPatchDto
import com.example.zupzup_manager.data.dto.item.parameter.ItemQuantityRequest
import com.example.zupzup_manager.data.dto.item.parameter.ItemRequest
import com.example.zupzup_manager.data.dto.item.response.ItemDto
import com.example.zupzup_manager.data.service.ItemService
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import javax.inject.Inject

class ItemDataSourceImpl @Inject constructor(
    private val itemService: ItemService
): ItemDataSource {

    override suspend fun getItemList(storeId: Long): Response<List<ItemDto>> {
        return itemService.getItemList(
            storeId = storeId
        )
    }

    override suspend fun addItem(
        storeId: Long,
        item: ItemRequest,
        image: MultipartBody.Part?
    ): Response<String> {
        return itemService.addItem(
            storeId = storeId,
            item = item,
            image = image
        )
    }

    override suspend fun modifyItemQuantity(
        storeId: Long,
        quantity: List<ItemPatchDto>
    ): Response<String> {

        val quantityJson = Gson().toJson(quantity)
        val quantityRequestBody = quantityJson.toRequestBody("application/json; charset=utf-8".toMediaType())

        return itemService.modifyItemQuantity(
            storeId = storeId,
            quantity = quantityRequestBody
        )
    }

    override suspend fun modifyItem(
        storeId: Long,
        item: ItemRequest,
        image: MultipartBody.Part?
    ): Response<String> {
        return itemService.modifyItem(
            storeId = storeId,
            item = item,
            image = image
        )
    }

    override suspend fun deleteItem(
        storeId: Long,
        itemId: Long
    ): Response<String> {
        return itemService.deleteItem(
            storeId = storeId,
            itemId = itemId
        )
    }
}