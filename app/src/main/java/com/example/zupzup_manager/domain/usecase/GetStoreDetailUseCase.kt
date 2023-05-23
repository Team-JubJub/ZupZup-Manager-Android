package com.example.zupzup_manager.domain.usecase

import com.example.zupzup_manager.domain.DataResult
import com.example.zupzup_manager.domain.models.StoreModel
import com.example.zupzup_manager.domain.repository.StoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetStoreDetailUseCase @Inject constructor(
    private val storeRepository: StoreRepository
) {
    suspend operator fun invoke(storeId: Long): Flow<DataResult<StoreModel>> {
        return flow {
            storeRepository.getStoreDetail(storeId).onSuccess {
                emit(DataResult.Success(it))
            }.onFailure {
                emit(DataResult.Failure("1"))
            }
        }.flowOn(Dispatchers.IO)
    }
}