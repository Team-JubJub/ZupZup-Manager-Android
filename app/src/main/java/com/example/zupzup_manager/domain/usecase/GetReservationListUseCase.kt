package com.example.zupzup_manager.domain.usecase

import com.example.zupzup_manager.domain.DataResult
import com.example.zupzup_manager.domain.models.ReservationModel
import com.example.zupzup_manager.domain.repository.ReservationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetReservationListUseCase @Inject constructor(
    private val reservationRepository: ReservationRepository
) {
    suspend operator fun invoke(storeId : Long) : Flow<DataResult<List<ReservationModel>>> {
        return flow {
            reservationRepository.getReservationList(storeId).onSuccess {
                emit(DataResult.Success(it))
            }.onFailure {
                emit(DataResult.Failure(1))
            }
        }.flowOn(Dispatchers.IO)
    }
}