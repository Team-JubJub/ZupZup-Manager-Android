package zupzup.manager.data.datasource.store

import okhttp3.MultipartBody
import retrofit2.Response
import zupzup.manager.data.dto.store.parameter.ModifyStoreRequest
import zupzup.manager.data.dto.store.response.ModifyStoreResponse
import zupzup.manager.data.dto.store.response.StoreDetailResponse
import zupzup.manager.data.service.StoreService
import javax.inject.Inject

class StoreDataSourceImpl @Inject constructor(
    private val storeService: StoreService
) : StoreDataSource {

    override suspend fun getStoreDetail(storeId: Long): Response<StoreDetailResponse> {
        return storeService.getStoreDetail(
            storeId = storeId
        )
    }

    override suspend fun changeOpenStatus(
        storeId: Long,
        isOpened: Boolean
    ): Response<String> {
        return storeService.changeOpenStatus(
            storeId = storeId,
            isOpened = isOpened
        )
    }

    override suspend fun modifyStoreDetail(
        storeId: Long,
        store: ModifyStoreRequest,
        image: MultipartBody.Part?
    ): Response<ModifyStoreResponse> {
        return storeService.modifyStoreDetail(
            storeId = storeId,
            image = image,
            data = store
        )
    }

    override suspend fun modifyStoreMatter(
        storeId: Long,
        storeMatter: String
    ): Response<String> {
        return storeService.modifyStoreMatter(
            storeId = storeId,
            storeMatter = storeMatter
        )
    }
}