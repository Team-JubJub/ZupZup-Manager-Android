package com.example.zupzup_manager.data.datasource.store

import android.util.Log
import com.example.zupzup_manager.data.dto.MerchandiseDto
import com.example.zupzup_manager.di.FireBaseModule
import com.example.zupzup_manager.domain.models.MerchandiseModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class StoreDataSourceImpl @Inject constructor(
    @FireBaseModule.TestStoreRef private val storeRef: CollectionReference
): StoreDataSource {

    override suspend fun getStoreDetail(storeId: Long): Task<DocumentSnapshot> {
        Log.d("getStoreDetail : ", storeRef.document(storeId.toString()).get().toString())
        return storeRef.document(storeId.toString()).get()
    }

    override suspend fun modifyMerchandiseDetail(
        storeId: Long,
        merchandiseList: List<MerchandiseDto>
    ): Task<Void> {
        return storeRef.document(storeId.toString())
            .update("merchandiseList", merchandiseList)
    }
}