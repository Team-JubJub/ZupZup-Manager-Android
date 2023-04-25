package com.example.zupzup_manager.data.repository

import com.example.zupzup_manager.data.datasource.admin.AdminDataSource
import com.example.zupzup_manager.data.datasource.admin.SharedPreferenceDataSource
import com.example.zupzup_manager.data.dto.Admin
import com.example.zupzup_manager.domain.models.AdminModel
import com.example.zupzup_manager.domain.repository.AdminRepository
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AdminRepositoryImpl @Inject constructor(
    private val adminDataSource: AdminDataSource,
    private val sharedPreferenceDataSource: SharedPreferenceDataSource
) : AdminRepository {
    override suspend fun login(id: String, pw: String): Result<AdminModel> {
        return try {
            val doc = adminDataSource.login(id, pw).await().documents
            if (doc.size == 0) {
                throw NullPointerException()
            }
            val admin = doc[0].toObject<Admin>() ?: throw NullPointerException()
            sharedPreferenceDataSource.insertStoreId(admin.storeId)
            Result.success(admin.toModel())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}