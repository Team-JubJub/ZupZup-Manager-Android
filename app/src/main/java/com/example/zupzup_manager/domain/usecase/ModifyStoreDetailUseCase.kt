package com.example.zupzup_manager.domain.usecase

import com.example.zupzup_manager.domain.DataResult
import com.example.zupzup_manager.domain.models.ModifyStoreModel
import com.example.zupzup_manager.domain.models.StoreModel
import com.example.zupzup_manager.domain.repository.StoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import javax.inject.Inject

class ModifyStoreDetailUseCase @Inject constructor(
    private val storeRepository: StoreRepository
) {
    suspend operator fun invoke(
        accessToken: String,
        storeId: Long,
        store: ModifyStoreModel,
        image: MultipartBody.Part?
    ): Flow<DataResult<StoreModel>> {
        return flow {
            storeRepository.modifyStoreDetail(accessToken, storeId, store, image)
                .onSuccess { store ->
                    emit(DataResult.Success(store))
                }.onFailure {
                emit(DataResult.Failure("1"))
            }
        }.flowOn(Dispatchers.IO)
    }
}