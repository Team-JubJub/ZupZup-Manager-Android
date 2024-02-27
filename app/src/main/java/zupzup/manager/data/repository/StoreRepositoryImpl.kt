package zupzup.manager.data.repository

import okhttp3.MultipartBody
import zupzup.manager.data.datasource.store.StoreDataSource
import zupzup.manager.data.dto.mapper.DtoMapper.toDto
import zupzup.manager.domain.models.store.ModifyStoreModel
import zupzup.manager.domain.models.store.StoreModel
import zupzup.manager.domain.repository.StoreRepository
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
            val storeModel: StoreModel? = if (response.isSuccessful) {
                response.body()?.toStoreModel()
            } else {
                null
            }
            Result.success(storeModel!!)
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