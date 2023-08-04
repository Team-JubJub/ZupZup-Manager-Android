package com.example.zupzup_manager.domain.repository

import com.example.zupzup_manager.domain.models.ModifyStoreModel
import com.example.zupzup_manager.domain.models.StoreModel
import okhttp3.MultipartBody

interface StoreRepository {
    suspend fun getStoreDetail(accessToken: String, storeId: Long): Result<StoreModel>
    suspend fun changeOpenStatus(accessToken: String, storeId: Long, isOpened: Boolean): Result<String>
    suspend fun modifyStoreDetail(accessToken: String, storeId: Long, store: ModifyStoreModel, image: MultipartBody.Part?): Result<StoreModel>
    suspend fun modifyStoreMatter(accessToken: String, storeId: Long, storeMatter: String): Result<String>
}