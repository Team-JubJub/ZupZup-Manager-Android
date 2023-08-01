package com.example.zupzup_manager.data.service

import com.example.zupzup_manager.data.dto.sign.parameter.SignInRequestBody
import com.example.zupzup_manager.data.dto.sign.response.SignInRefreshResponse
import com.example.zupzup_manager.data.dto.sign.response.SignInResponse
import com.example.zupzup_manager.data.dto.sign.response.SignOutResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface SignInService {
    @POST("mobile/sign-in")
    suspend fun signIn(
        @Body body: SignInRequestBody
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

