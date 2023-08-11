package com.example.zupzup_manager.data.datasource.admin

import com.example.zupzup_manager.data.dto.admin.response.SignInResponse
import com.example.zupzup_manager.data.dto.admin.response.SignOutResponse
import retrofit2.Response

interface SignInDataSource {
    suspend fun login(id: String, pw: String): Response<SignInResponse>
    suspend fun logout(accessToken: String, refreshToken: String): Response<SignOutResponse>
}