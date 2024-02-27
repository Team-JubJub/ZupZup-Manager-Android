package zupzup.manager.domain.usecase.store

import zupzup.manager.domain.DataResult
import zupzup.manager.domain.models.store.StoreModel
import zupzup.manager.domain.repository.StoreRepository
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
            storeRepository.getStoreDetail(storeId).onSuccess { store ->
                emit(DataResult.Success(store))
            }.onFailure {
                emit(DataResult.Failure("1"))
            }
        }.flowOn(Dispatchers.IO)
    }
}