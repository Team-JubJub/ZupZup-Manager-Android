package com.example.zupzup_manager.data.datasource.reservation

import com.example.zupzup_manager.data.dto.CartDto
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.flow.Flow

interface ReservationDataSource {

    suspend fun getReservationList(storeId: Long, state: Int): Flow<List<DocumentSnapshot>>

    suspend fun confirmReservation(reserveId: Long, cartList: List<CartDto>): Task<Void>

    suspend fun rejectReservation(reserveId: Long) : Task<Void>

    suspend fun cancelReservation(reserveId: Long) : Task<Void>

    suspend fun completeReservation(reserveId: Long) : Task<Void>
}