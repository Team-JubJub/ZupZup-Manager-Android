package zupzup.manager.data.datasource.store

import zupzup.manager.data.dto.store.parameter.ModifyStoreRequest
import zupzup.manager.data.dto.store.response.ModifyStoreResponse
import zupzup.manager.data.dto.store.response.StoreDetailResponse
import okhttp3.MultipartBody
import retrofit2.Response

interface StoreDataSource {
    suspend fun getStoreDetail(storeId: Long): Response<StoreDetailResponse>
    suspend fun changeOpenStatus(storeId: Long, isOpened: Boolean): Response<String>
    suspend fun modifyStoreDetail(storeId: Long, store: ModifyStoreRequest, image: MultipartBody.Part?): Response<ModifyStoreResponse>
    suspend fun modifyStoreMatter(storeId: Long, storeMatter: String): Response<String>

}