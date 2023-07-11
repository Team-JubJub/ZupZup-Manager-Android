package com.example.zupzup_manager.data.repository

import com.example.zupzup_manager.data.datasource.reservation.ReservationDataSource
import com.example.zupzup_manager.data.dto.mapper.DtoMapper.toDto
import com.example.zupzup_manager.data.dto.ReservationDto
import com.example.zupzup_manager.domain.models.CartModel
import com.example.zupzup_manager.domain.models.ReservationModel
import com.example.zupzup_manager.domain.repository.ReservationRepository
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ReservationRepositoryImpl @Inject constructor(
    private val reservationDataSource: ReservationDataSource
) : ReservationRepository {

    override suspend fun getReservationList(storeId: Long, state: Int): Flow<Result<List<ReservationModel>>> {
        return flow {
            reservationDataSource.getReservationList(storeId, state).collect {
                val reservationList =
                    it.map { doc -> doc.toObject<ReservationDto>()!!.toReservationModel() }
                emit(Result.success(reservationList))
            }
        }.catch { e ->
            emit(Result.failure(e))
        }
    }

    override suspend fun confirmReservation(
        reserveId: Long,
        cartList: List<CartModel>
    ): Result<Int> {
        return try {
            reservationDataSource.confirmReservation(reserveId, cartList.map { it.toDto() }).await()
            Result.success(0)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun rejectReservation(reserveId: Long): Result<Int> {
        return try {
            reservationDataSource.rejectReservation(reserveId).await()
            Result.success(0)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun cancelReservation(reserveId: Long): Result<Int> {
        return try {
            reservationDataSource.cancelReservation(reserveId).await()
            Result.success(0)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun completeReservation(reserveId: Long): Result<Int> {
        return try {
            reservationDataSource.completeReservation(reserveId).await()
            Result.success(0)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}