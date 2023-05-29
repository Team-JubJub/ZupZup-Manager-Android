package com.example.zupzup_manager.domain.repository

import com.example.zupzup_manager.domain.models.MerchandiseModel
import com.example.zupzup_manager.domain.models.StoreModel
import kotlinx.coroutines.flow.Flow

interface StoreRepository {
    suspend fun getStoreDetail(storeId: Long): Flow<Result<StoreModel>>

    suspend fun modifyMerchandiseDetail(storeId: Long, merchandiseList: List<MerchandiseModel>): Result<Int>
}