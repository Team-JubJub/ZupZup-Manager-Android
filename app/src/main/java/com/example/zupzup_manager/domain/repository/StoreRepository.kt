package com.example.zupzup_manager.domain.repository

import com.example.zupzup_manager.domain.models.ModifyStoreModel
import com.example.zupzup_manager.domain.models.StoreModel
import okhttp3.MultipartBody

interface StoreRepository {
    suspend fun getStoreDetail(storeId: Long): Result<StoreModel>
    suspend fun changeOpenStatus(storeId: Long, isOpened: Boolean): Result<String>
    suspend fun modifyStoreDetail(storeId: Long, store: ModifyStoreModel, image: MultipartBody.Part?): Result<StoreModel>
    suspend fun modifyStoreMatter(storeId: Long, storeMatter: String): Result<String>
}