package com.example.zupzup_manager.domain.usecase

import android.util.Log
import com.example.zupzup_manager.domain.DataResult
import com.example.zupzup_manager.domain.repository.StoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ModifyStoreMatterUseCase @Inject constructor(
    private val storeRepository: StoreRepository
) {
    suspend operator fun invoke(
        accessToken: String,
        storeId: Long,
        storeMatter: String
    ): Flow<DataResult<String>> {
        return flow {
            storeRepository.modifyStoreMatter(accessToken, storeId, storeMatter).onSuccess { res ->
                emit(DataResult.Success(res))
            }.onFailure {
                emit(DataResult.Failure("-1"))
            }
        }.flowOn(Dispatchers.IO)
    }
}