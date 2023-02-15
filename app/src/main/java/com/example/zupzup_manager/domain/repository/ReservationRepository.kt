package com.example.zupzup_manager.domain.repository

import com.example.zupzup_manager.domain.models.ReservationModel

interface ReservationRepository {
    suspend fun getReservationList(storeId: Long): Result<List<ReservationModel>>
}