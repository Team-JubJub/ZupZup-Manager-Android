package com.example.zupzup_manager.data.datasource.admin

import com.example.zupzup_manager.data.dto.sign.parameter.SignInRequestBody
import com.example.zupzup_manager.data.dto.sign.response.SignInResponse
import com.example.zupzup_manager.data.dto.sign.response.SignOutResponse
import com.example.zupzup_manager.data.service.SignInService
import retrofit2.Response
import javax.inject.Inject

class SignInDataSourceImpl @Inject constructor(
    private val signInService: SignInService
) : SignInDataSource {
    override suspend fun login(
        id: String,
        pw: String
    ): Response<SignInResponse> {
        return signInService.signIn(
            body = SignInRequestBody(
                loginId = id,
                loginPwd = pw
            )
        )
    }
    override suspend fun logout(
        accessToken: String,
        refreshToken: String
    ): Response<SignOutResponse> {
        return signInService.signOut(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }
}