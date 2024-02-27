package zupzup.manager.data.datasource.admin

import zupzup.manager.data.dto.admin.response.SignInResponse
import zupzup.manager.data.dto.admin.response.SignOutResponse
import retrofit2.Response

interface SignInDataSource {
    suspend fun login(id: String, pw: String, deviceToken: String): Response<SignInResponse>
    suspend fun logout(accessToken : String, refreshToken : String, deviceToken : String): Response<SignOutResponse>
    suspend fun leaveZupzup(id: Long): Response<String>
}