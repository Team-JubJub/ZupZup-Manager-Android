package com.example.zupzup_manager.data.datasource.store

import com.example.zupzup_manager.data.dto.store.parameter.ModifyStoreRequestBody
import com.example.zupzup_manager.data.dto.store.response.ModifyStoreResponse
import com.example.zupzup_manager.data.dto.store.response.StoreDetailResponse
import com.example.zupzup_manager.data.service.OrderService
import com.example.zupzup_manager.data.service.StoreService
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class StoreDataSourceImpl @Inject constructor(
    private val storeService: StoreService
): StoreDataSource {

    override suspend fun getStoreDetail(accessToken: String, storeId: Long): Response<StoreDetailResponse> {
        return storeService.getStoreDetail(
            storeId = storeId,
            accessToken = accessToken
        )
    }

    override suspend fun changeOpenStatus(
        accessToken: String,
        storeId: Long,
        isOpened: Boolean
    ): Response<String> {
        return storeService.changeOpenStatus(
            storeId = storeId,
            accessToken = accessToken,
            isOpened = isOpened
        )
    }

    override suspend fun modifyStoreDetail(
        accessToken: String,
        storeId: Long,
        store: ModifyStoreRequestBody,
        image: MultipartBody.Part?
    ): Response<ModifyStoreResponse> {
        return storeService.modifyStoreDetail(
            storeId = storeId,
            accessToken = accessToken,
            image = image,
            data = store
        )
    }

    override suspend fun modifyStoreMatter(
        accessToken: String,
        storeId: Long,
        storeMatter: String
    ): Response<String> {
        return storeService.modifyStoreMatter(
            storeId = storeId,
            accessToken = accessToken,
            storeMatter = storeMatter
        )
    }
}