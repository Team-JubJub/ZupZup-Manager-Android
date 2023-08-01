package com.example.zupzup_manager.data.datasource.admin

import com.example.zupzup_manager.data.dto.sign.response.SignInResponse
import com.example.zupzup_manager.data.dto.sign.response.SignOutResponse
import retrofit2.Response

interface SignInDataSource {
    suspend fun login(id: String, pw: String): Response<SignInResponse>
    suspend fun logout(accessToken: String, refreshToken: String): Response<SignOutResponse>
}