package com.example.zupzup_manager.data.datasource.reservation

import com.example.zupzup_manager.data.common.Constants
import com.example.zupzup_manager.data.dto.CartDto
import com.example.zupzup_manager.di.FireBaseModule
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class ReservationDataSourceImpl @Inject constructor(
    @FireBaseModule.TestReservationRef private val reservationRef: CollectionReference
) : ReservationDataSource {

    override suspend fun getReservationList(storeId: Long): Flow<List<DocumentSnapshot>> {
        return callbackFlow {
            val reservationDoc = reservationRef.whereEqualTo("storeId", storeId)

            val subscription = reservationDoc.addSnapshotListener { value, _ ->
                if (value == null) {
                    return@addSnapshotListener
                }
                try {
                    trySend(value.documents.toList())
                } catch (e: Exception) {
                    close(e)
                }
            }

            awaitClose {
                subscription.remove()
            }
        }
    }

    override suspend fun confirmReservation(reserveId: Long, cartList: List<CartDto>): Task<Void> {
        return reservationRef.document(reserveId.toString())
            .update(mapOf("cartList" to cartList, "state" to Constants.stateConfirm))
    }

    override suspend fun rejectReservation(reserveId: Long): Task<Void> {
        return reservationRef.document(reserveId.toString())
            .update("state", Constants.stateReject)
    }

    override suspend fun cancelReservation(reserveId: Long): Task<Void> {
        return reservationRef.document(reserveId.toString())
            .update("state", Constants.stateCancel)
    }

    override suspend fun completeReservation(reserveId: Long): Task<Void> {
        return reservationRef.document(reserveId.toString())
            .update("state", Constants.stateComplete)
    }
}