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

    override suspend fun getStoreDetail(storeId: Long): Flow<DocumentSnapshot> {
        return callbackFlow {
            val storeDetailDoc = storeRef.document(storeId.toString())

            val subscription = storeDetailDoc.addSnapshotListener { value, error ->
                if (error != null) {
                    close()
                    return@addSnapshotListener
                }
                if (value != null) {
                    trySend(value)
                }
            }

            awaitClose {
                subscription.remove()
            }
        }
    }

    override suspend fun modifyMerchandiseDetail(
        storeId: Long,
        merchandiseList: List<MerchandiseDto>
    ): Task<Void> {
        return storeRef.document(storeId.toString())
            .update("merchandiseList", merchandiseList)
    }
}