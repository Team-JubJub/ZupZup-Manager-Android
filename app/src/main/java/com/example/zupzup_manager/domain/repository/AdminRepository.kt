package com.example.zupzup_manager.domain.repository

interface AdminRepository {

    suspend fun login(id : String, pw : String) : Result<Int>
}