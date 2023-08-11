package com.example.zupzup_manager.domain.repository

import com.example.zupzup_manager.domain.models.item.ItemModel
import com.example.zupzup_manager.domain.models.item.ItemQuantityModel
import com.example.zupzup_manager.domain.models.item.ModifyItemModel
import okhttp3.MultipartBody

interface ItemRepository {
    suspend fun getItemList(storeId: Long): Result<List<ItemModel>>
    suspend fun addItem(storeId: Long, item: ModifyItemModel, image: MultipartBody.Part?): Result<String>
    suspend fun modifyItemQuantity(storeId: Long, quantity: List<ItemQuantityModel>): Result<String>
    suspend fun modifyItem(storeId: Long, item: ModifyItemModel, image: MultipartBody.Part?): Result<String>
    suspend fun deleteItem(storeId: Long, itemId: Long): Result<String>
}