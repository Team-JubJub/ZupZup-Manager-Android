package com.example.zupzup_manager.data.datasource.store

import android.util.Log
import com.example.zupzup_manager.di.FireBaseModule
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import javax.inject.Inject

class StoreDataSourceImpl @Inject constructor(
    @FireBaseModule.TestStoreRef private val storeRef: CollectionReference
): StoreDataSource {

    override suspend fun getStoreDetail(storeId: Long): Task<DocumentSnapshot> {
        Log.e("detail : ", storeRef.document(storeId.toString()).get().toString())
        return storeRef.document(storeId.toString()).get()
    }
}