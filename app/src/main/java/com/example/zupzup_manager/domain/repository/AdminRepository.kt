package com.example.zupzup_manager.domain.repository

import com.example.zupzup_manager.domain.models.AdminModel

interface AdminRepository {

    suspend fun login(id: String, pw: String): Result<AdminModel>
}