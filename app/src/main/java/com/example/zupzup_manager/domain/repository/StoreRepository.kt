package com.example.zupzup_manager.domain.repository

import com.example.zupzup_manager.domain.models.StoreModel

interface StoreRepository {
    suspend fun getStoreDetail(storeId: Long): Result<StoreModel>
}