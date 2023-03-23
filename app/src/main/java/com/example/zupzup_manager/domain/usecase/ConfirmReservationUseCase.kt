package com.example.zupzup_manager.domain.usecase

import com.example.zupzup_manager.domain.models.CartModel
import com.example.zupzup_manager.domain.repository.ReservationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ConfirmReservationUseCase @Inject constructor(
    private val reservationRepository: ReservationRepository
) {
    suspend operator fun invoke(reserveId: Long, cartList: List<CartModel>): Flow<Result<Int>> {
        return flow {
            emit(reservationRepository.confirmReservation(reserveId, cartList))
        }.flowOn(Dispatchers.IO)
    }
}