package com.example.zupzup_manager.data.datasource.store

import com.example.zupzup_manager.data.dto.MerchandiseDto
import com.example.zupzup_manager.domain.models.MerchandiseModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.flow.Flow

interface StoreDataSource {

    suspend fun getStoreDetail(storeId: Long): Flow<DocumentSnapshot>

    suspend fun modifyMerchandiseDetail(storeId: Long, merchandiseList: List<MerchandiseDto>): Task<Void>
}