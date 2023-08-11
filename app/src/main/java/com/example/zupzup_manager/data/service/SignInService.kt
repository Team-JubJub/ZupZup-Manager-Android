package com.example.zupzup_manager.data.service

import com.example.zupzup_manager.data.dto.admin.parameter.SignInRequest
import com.example.zupzup_manager.data.dto.admin.response.SignInRefreshResponse
import com.example.zupzup_manager.data.dto.admin.response.SignInResponse
import com.example.zupzup_manager.data.dto.admin.response.SignOutResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface SignInService {
    @POST("mobile/sign-in")
    suspend fun signIn(
        @Body body: SignInRequest
    ): Response<SignInResponse>

    @POST("mobile/sign-out")
    suspend fun signOut(
        @Header("accessToken") accessToken: String,
        @Header("refreshToken") refreshToken: String
    ): Response<SignOutResponse>

    @POST("mobile/sign-in/refresh")
    suspend fun signInRefresh(
        @Header("refreshToken") refreshToken: String
    ): Response<SignInRefreshResponse>
}