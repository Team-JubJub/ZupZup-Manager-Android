package com.example.zupzup_manager.domain.usecase

import com.example.zupzup_manager.domain.models.CartModel
import com.example.zupzup_manager.domain.models.MerchandiseModel
import com.example.zupzup_manager.domain.repository.StoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ModifyMerchandiseUseCase @Inject constructor(
    private val storeRepository: StoreRepository
) {
    suspend operator fun invoke(storeId: Long, merchandiseList: List<MerchandiseModel>): Flow<Result<Int>> {
        return flow {
            emit(storeRepository.modifyMerchandiseDetail(storeId, merchandiseList))
        }.flowOn(Dispatchers.IO)
    }
}