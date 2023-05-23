package com.example.zupzup_manager.data.service

import com.example.zupzup_manager.data.dto.sign.parameter.SignInRequestBody
import com.example.zupzup_manager.data.dto.sign.response.SignInResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SignInService {

    @POST("mobile/sign-in")
    suspend fun signIn(
        @Body body: SignInRequestBody
    ): Response<SignInResponse>
}

