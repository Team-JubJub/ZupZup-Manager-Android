package com.example.zupzup_manager.data.datasource

import com.example.zupzup_manager.di.FireBaseModule
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject

class ReservationDataSourceImpl @Inject constructor(
    @FireBaseModule.TestReservationRef private val reservationRef : CollectionReference
) : ReservationDataSource {

    override suspend fun getReservationList(storeId : Long): Task<QuerySnapshot> {
        return reservationRef.whereEqualTo("storeId", storeId).get()
    }
}