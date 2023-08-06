package com.example.zupzup_manager.data.repository

import com.example.zupzup_manager.data.datasource.store.StoreDataSource
import com.example.zupzup_manager.data.dto.mapper.DtoMapper.toDto
import com.example.zupzup_manager.domain.models.ModifyStoreModel
import com.example.zupzup_manager.domain.models.StoreModel
import com.example.zupzup_manager.domain.repository.StoreRepository
import okhttp3.MultipartBody
import javax.inject.Inject

class StoreRepositoryImpl @Inject constructor(
    private val storeDataSource: StoreDataSource
): StoreRepository {

    override suspend fun getStoreDetail(
        storeId: Long
    ): Result<StoreModel> {
        return try {
            val response = storeDataSource.getStoreDetail(storeId)
            val store: StoreModel? = if (response.isSuccessful) {
                response.body()?.toStoreModel()
            } else {
                null
            }
            Result.success(store!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun changeOpenStatus(
        storeId: Long,
        isOpened: Boolean
    ): Result<String> {
        return try {
            val response = storeDataSource.changeOpenStatus(storeId, isOpened)
            if (response.isSuccessful) {
                Result.success("1")
            } else {
                Result.success(response.code().toString())
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun modifyStoreDetail(
        storeId: Long,
        store: ModifyStoreModel,
        image: MultipartBody.Part?
    ): Result<StoreModel> {
        return try {
            val response = storeDataSource.modifyStoreDetail(storeId, store.toDto(), image)
            val store: StoreModel? = if (response.isSuccessful) {
                response.body()?.toStoreModel()
            } else {
                null
            }
            Result.success(store!!)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun modifyStoreMatter(
        storeId: Long,
        storeMatter: String
    ): Result<String> {
        return try {
            val response = storeDataSource.modifyStoreMatter(storeId, storeMatter)
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