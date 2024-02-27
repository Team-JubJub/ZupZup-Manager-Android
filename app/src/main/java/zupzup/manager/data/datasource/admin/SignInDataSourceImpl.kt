package zupzup.manager.data.datasource.admin

import retrofit2.Response
import zupzup.manager.data.dto.admin.parameter.SignInRequest
import zupzup.manager.data.dto.admin.parameter.SignOutRequest
import zupzup.manager.data.dto.admin.response.SignInResponse
import zupzup.manager.data.dto.admin.response.SignOutResponse
import zupzup.manager.data.service.SignInService
import zupzup.manager.di.ServiceModule
import javax.inject.Inject

class SignInDataSourceImpl @Inject constructor(
    @ServiceModule.ZupZupServiceObject private val signInService: SignInService
) : SignInDataSource {
    override suspend fun login(
        id: String,
        pw: String,
        deviceToken: String
    ): Response<SignInResponse> {
        return signInService.signIn(
            body = SignInRequest(
                loginId = id,
                loginPwd = pw,
                deviceToken = deviceToken
            )
        )
    }

    override suspend fun logout(
        accessToken: String,
        refreshToken: String,
        deviceToken: String
    ): Response<SignOutResponse> {
        return signInService.signOut(
            accessToken = accessToken,
            refreshToken = refreshToken,
            body = SignOutRequest(deviceToken)
        )
    }

    override suspend fun leaveZupzup(
        id: Long
    ): Response<String> {
        return signInService.leaveZupzup(
            id = id
        )
    }
}