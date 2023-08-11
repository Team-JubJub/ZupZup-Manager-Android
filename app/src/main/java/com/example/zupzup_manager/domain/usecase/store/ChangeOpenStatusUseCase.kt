package com.example.zupzup_manager.domain.usecase.store

import com.example.zupzup_manager.domain.DataResult
import com.example.zupzup_manager.domain.repository.StoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ChangeOpenStatusUseCase @Inject constructor(
    private val storeRepository: StoreRepository
) {
    suspend operator fun invoke(
        storeId: Long,
        isOpened: Boolean
    ): Flow<DataResult<String>> {
        return flow {
            storeRepository.changeOpenStatus(storeId, isOpened).onSuccess { res ->
                emit(DataResult.Success(res))
            }.onFailure {
                emit(DataResult.Failure("-1"))
            }
        }.flowOn(Dispatchers.IO)
    }
}