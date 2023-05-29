package com.example.zupzup_manager.data.repository

import com.example.zupzup_manager.data.datasource.store.StoreDataSource
import com.example.zupzup_manager.data.dto.StoreDto
import com.example.zupzup_manager.data.dto.mapper.DtoMapper.toDto
import com.example.zupzup_manager.domain.models.MerchandiseModel
import com.example.zupzup_manager.domain.models.StoreModel
import com.example.zupzup_manager.domain.repository.StoreRepository
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StoreRepositoryImpl @Inject constructor(
    private val storeDataSource: StoreDataSource
): StoreRepository {

    override suspend fun getStoreDetail(storeId: Long): Flow<Result<StoreModel>> {
        return flow {
            storeDataSource.getStoreDetail(storeId).collect {
                val storeDetail = it.toObject<StoreDto>()?.toStoreModel()
                if (storeDetail != null) {
                    emit(Result.success(storeDetail))
                }
            }
        }.catch { e ->
            emit(Result.failure(e))
        }
    }

    override suspend fun modifyMerchandiseDetail(
        storeId: Long,
        merchandiseList: List<MerchandiseModel>
    ): Result<Int> {
        return try {
            storeDataSource.modifyMerchandiseDetail(storeId, merchandiseList.map { it.toDto() }).await()
            Result.success(0)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}