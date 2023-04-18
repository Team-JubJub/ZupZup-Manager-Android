package com.example.zupzup_manager.data.repository

import com.example.zupzup_manager.domain.repository.AdminRepository
import javax.inject.Inject

class AdminRepositoryImpl @Inject constructor(

) : AdminRepository {
    override suspend fun login(id: String, pw: String) {

    }

}