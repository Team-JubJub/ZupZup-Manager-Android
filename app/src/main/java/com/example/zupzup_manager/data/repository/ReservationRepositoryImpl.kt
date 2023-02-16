package com.example.zupzup_manager.data.repository

import com.example.zupzup_manager.data.datasource.ReservationDataSource
import com.example.zupzup_manager.data.dto.ReservationDto
import com.example.zupzup_manager.domain.models.ReservationModel
import com.example.zupzup_manager.domain.repository.ReservationRepository
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ReservationRepositoryImpl @Inject constructor(
    private val reservationDataSource: ReservationDataSource
) : ReservationRepository {
    override suspend fun getReservationList(storeId: Long): Result<List<ReservationModel>> {
        return try {
            val reservationList = reservationDataSource.getReservationList(storeId).await().map {
                it.toObject<ReservationDto>()
            }
            Result.success(reservationList.map { it.toReservationModel() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}