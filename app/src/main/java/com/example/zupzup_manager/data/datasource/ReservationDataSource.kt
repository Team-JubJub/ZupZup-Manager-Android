package com.example.zupzup_manager.data.datasource

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

interface ReservationDataSource {

    suspend fun getReservationList(storeId : Long) : Task<QuerySnapshot>

}