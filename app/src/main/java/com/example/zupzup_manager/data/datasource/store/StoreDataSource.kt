package com.example.zupzup_manager.data.datasource.store

import com.example.zupzup_manager.data.dto.store.parameter.ModifyStoreRequestBody
import com.example.zupzup_manager.data.dto.store.response.ModifyStoreResponse
import com.example.zupzup_manager.data.dto.store.response.StoreDetailResponse
import okhttp3.MultipartBody
import retrofit2.Response

interface StoreDataSource {
    suspend fun getStoreDetail(storeId: Long): Response<StoreDetailResponse>
    suspend fun changeOpenStatus(storeId: Long, isOpened: Boolean): Response<String>
    suspend fun modifyStoreDetail(storeId: Long, store: ModifyStoreRequestBody, image: MultipartBody.Part?): Response<ModifyStoreResponse>
    suspend fun modifyStoreMatter(storeId: Long, storeMatter: String): Response<String>

}