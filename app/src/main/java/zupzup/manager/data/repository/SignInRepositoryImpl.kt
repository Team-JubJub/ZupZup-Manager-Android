package zupzup.manager.data.repository

import android.util.Log
import kotlinx.coroutines.runBlocking
import zupzup.manager.data.datasource.admin.SharedPreferenceDataSource
import zupzup.manager.data.datasource.admin.SignInDataSource
import zupzup.manager.data.dto.admin.response.SignInResponse
import zupzup.manager.di.NetworkModule
import zupzup.manager.domain.models.admin.AdminModel
import zupzup.manager.domain.repository.SignInRepository
import retrofit2.Retrofit
import javax.inject.Inject

class SignInRepositoryImpl @Inject constructor(
    private val signInDataSource: SignInDataSource,
    private val sharedPreferenceDataSource: SharedPreferenceDataSource,
    @NetworkModule.ZupZupRetrofitObject private val zupzupRetrofitObject: Retrofit
) : SignInRepository {
    override suspend fun login(id: String, pw: String, deviceToken: String): Result<AdminModel> {
        return try {
            val response = signInDataSource.login(id, pw, deviceToken)
            val admin: AdminModel
            if (response.isSuccessful) {
                with(response.body()!!) {
                    sharedPreferenceDataSource.insertAccessToken(this.accessToken)
                    sharedPreferenceDataSource.insertRefreshToken(this.refreshToken)
                    sharedPreferenceDataSource.insertStoreId(this.storeId)
                    admin = this.toAdminModel()
                }
            } else {
                admin = zupzupRetrofitObject.responseBodyConverter<SignInResponse>(
                    SignInResponse::class.java,
                    SignInResponse::class.java.annotations
                ).convert(response.errorBody())!!.toAdminModel()
            }
            Result.success(admin)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getStoreIdInLocal(): Result<Triple<String, String, Long>> {
        return try {
            Result.success(
                Triple(
                    sharedPreferenceDataSource.getAccessToken(),
                    sharedPreferenceDataSource.getRefreshToken(),
                    sharedPreferenceDataSource.getStoreId()
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout(accessToken: String, refreshToken: String): Result<String> {
        return try {
            val response = signInDataSource.logout(accessToken, refreshToken)
            if (response.isSuccessful) {
                sharedPreferenceDataSource.deleteData()
            }
            Result.success(response.body().toString())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun leave(id: Long): Result<String> {
        return try {
            val response = signInDataSource.leaveZupzup(id)
            Result.success(response.body().toString())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}