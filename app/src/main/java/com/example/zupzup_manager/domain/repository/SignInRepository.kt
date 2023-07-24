package com.example.zupzup_manager.domain.repository

import com.example.zupzup_manager.domain.models.AdminModel

interface SignInRepository {

    suspend fun login(id: String, pw: String): Result<AdminModel>

    suspend fun getStoreIdInLocal() : Result<Pair<String, Long>>
}