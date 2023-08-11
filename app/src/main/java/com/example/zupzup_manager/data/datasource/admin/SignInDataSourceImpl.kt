package com.example.zupzup_manager.data.datasource.admin

import com.example.zupzup_manager.data.dto.admin.parameter.SignInRequest
import com.example.zupzup_manager.data.dto.admin.response.SignInResponse
import com.example.zupzup_manager.data.dto.admin.response.SignOutResponse
import com.example.zupzup_manager.data.service.SignInService
import com.example.zupzup_manager.di.ServiceModule
import retrofit2.Response
import javax.inject.Inject

class SignInDataSourceImpl @Inject constructor(
    @ServiceModule.ZupZupServiceObject private val signInService: SignInService
) : SignInDataSource {
    override suspend fun login(
        id: String,
        pw: String
    ): Response<SignInResponse> {
        return signInService.signIn(
            body = SignInRequest(
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