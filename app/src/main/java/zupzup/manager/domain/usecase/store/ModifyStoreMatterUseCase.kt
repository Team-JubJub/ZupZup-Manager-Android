package zupzup.manager.domain.usecase.store

import zupzup.manager.domain.DataResult
import zupzup.manager.domain.repository.StoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ModifyStoreMatterUseCase @Inject constructor(
    private val storeRepository: StoreRepository
) {
    suspend operator fun invoke(
        storeId: Long,
        storeMatter: String
    ): Flow<DataResult<String>> {
        return flow {
            storeRepository.modifyStoreMatter(storeId, storeMatter).onSuccess { res ->
                emit(DataResult.Success(res))
            }.onFailure {
                emit(DataResult.Failure("-1"))
            }
        }.flowOn(Dispatchers.IO)
    }
}