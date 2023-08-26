package zupzup.manager.data.datasource.admin

import zupzup.manager.data.dto.admin.parameter.SignInRequest
import zupzup.manager.data.dto.admin.response.SignInResponse
import zupzup.manager.data.dto.admin.response.SignOutResponse
import zupzup.manager.data.service.SignInService
import zupzup.manager.di.ServiceModule
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