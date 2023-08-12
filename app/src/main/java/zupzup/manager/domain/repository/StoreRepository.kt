package zupzup.manager.domain.repository

import zupzup.manager.domain.models.store.ModifyStoreModel
import zupzup.manager.domain.models.store.StoreModel
import okhttp3.MultipartBody

interface StoreRepository {
    suspend fun getStoreDetail(storeId: Long): Result<StoreModel>
    suspend fun changeOpenStatus(storeId: Long, isOpened: Boolean): Result<String>
    suspend fun modifyStoreDetail(storeId: Long, store: ModifyStoreModel, image: MultipartBody.Part?): Result<StoreModel>
    suspend fun modifyStoreMatter(storeId: Long, storeMatter: String): Result<String>
}