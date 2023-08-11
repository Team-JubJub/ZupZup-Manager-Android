package com.example.zupzup_manager.data.repository

import android.util.Log
import com.example.zupzup_manager.data.datasource.item.ItemDataSource
import com.example.zupzup_manager.data.dto.mapper.DtoMapper.toDto
import com.example.zupzup_manager.domain.models.item.ItemModel
import com.example.zupzup_manager.domain.models.item.ItemQuantityModel
import com.example.zupzup_manager.domain.models.item.ModifyItemModel
import com.example.zupzup_manager.domain.repository.ItemRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val itemDataSource: ItemDataSource
): ItemRepository {

    override suspend fun getItemList(
        storeId: Long
    ): Result<List<ItemModel>> {
        return try {
            val response = itemDataSource.getItemList(storeId)
            val item: List<ItemModel>? = if (response.isSuccessful) {
                response.body()?.map { it.toItemModel() }
            } else {
                null
            }
            Result.success(item!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addItem(
        storeId: Long,
        item: ModifyItemModel,
        image: MultipartBody.Part?
    ): Result<String> {
        return try {
            val response = itemDataSource.addItem(storeId, item.toDto(), image)
            if (response.isSuccessful) {
                Result.success("1")
            } else {
                Result.success(response.code().toString())
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun modifyItemQuantity(
        storeId: Long,
        body: List<ItemQuantityModel>
    ): Result<String> {
        return try {
            val response = itemDataSource.modifyItemQuantity(storeId, body.toDto())
            if (response.isSuccessful) {
                Log.d("TAG", "repo 수량 수정 완료")
                Result.success("1")
            } else {
                Log.d("TAG", "repo 수량 수정 실패")
                Result.success(response.code().toString())
            }
        } catch (e: Exception) {
            Log.d("TAG", "repo 수량 수정 찐실패")
            Log.d("TAG", e.toString())
            Result.failure(e)
        }
    }

    override suspend fun modifyItem(
        storeId: Long,
        item: ModifyItemModel,
        image: MultipartBody.Part?
    ): Result<String> {
        return try {
            val response = itemDataSource.modifyItem(storeId, item.toDto(), image)
            if (response.isSuccessful) {
                Result.success("1")
            } else {
                Result.success(response.code().toString())
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteItem(
        storeId: Long,
        itemId: Long
    ): Result<String> {
        return try {
            val response = itemDataSource.deleteItem(storeId, itemId)
            if (response.isSuccessful) {
                Result.success("1")
            } else {
                Result.success(response.code().toString())
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}