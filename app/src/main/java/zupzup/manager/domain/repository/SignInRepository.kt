package zupzup.manager.domain.repository

import zupzup.manager.domain.models.admin.AdminModel

interface SignInRepository {

    suspend fun login(id: String, pw: String, deviceToken: String): Result<AdminModel>

    suspend fun getStoreIdInLocal() : Result<Triple<String, String, Long>>

    suspend fun logout(accessToken: String, refreshToken: String): Result<String>

    suspend fun leave(id: Long): Result<String>
}