package zupzup.manager.data.service

import zupzup.manager.data.dto.admin.parameter.SignInRequest
import zupzup.manager.data.dto.admin.response.SignInRefreshResponse
import zupzup.manager.data.dto.admin.response.SignInResponse
import zupzup.manager.data.dto.admin.response.SignOutResponse
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