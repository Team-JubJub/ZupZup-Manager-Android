package zupzup.manager.data.repository

import android.util.Log
import zupzup.manager.data.datasource.item.ItemDataSource
import zupzup.manager.data.dto.mapper.DtoMapper.toDto
import zupzup.manager.domain.models.item.ItemModel
import zupzup.manager.domain.models.item.ItemQuantityModel
import zupzup.manager.domain.models.item.ItemModifyModel
import zupzup.manager.domain.repository.ItemRepository
import okhttp3.MultipartBody
import zupzup.manager.domain.models.item.ItemAddModel
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
        item: ItemAddModel,
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
                Result.success("1")
            } else {
                Result.success(response.code().toString())
            }
        } catch (e: Exception) {
            Log.d("TAG", e.toString())
            Result.failure(e)
        }
    }

    override suspend fun modifyItem(
        storeId: Long,
        item: ItemModifyModel,
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