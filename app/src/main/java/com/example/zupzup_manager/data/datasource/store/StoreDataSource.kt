package com.example.zupzup_manager.data.datasource.store

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot

interface StoreDataSource {
    suspend fun getStoreDetail(storeId: Long): Task<DocumentSnapshot>
}