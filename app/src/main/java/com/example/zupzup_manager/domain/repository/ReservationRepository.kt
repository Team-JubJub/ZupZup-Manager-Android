package com.example.zupzup_manager.domain.repository

import com.example.zupzup_manager.domain.models.CartModel
import com.example.zupzup_manager.domain.models.ReservationModel
import kotlinx.coroutines.flow.Flow

interface ReservationRepository {
    suspend fun getReservationList(storeId: Long, state: Int): Flow<Result<List<ReservationModel>>>

    suspend fun confirmReservation(reserveId: Long, cartList: List<CartModel>): Result<Int>

    suspend fun rejectReservation(reserveId: Long) : Result<Int>

    suspend fun cancelReservation(reserveId: Long) : Result<Int>

    suspend fun completeReservation(reserveId: Long) : Result<Int>
}