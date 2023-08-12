package zupzup.manager.di

import android.util.Log
import zupzup.manager.data.datasource.admin.SharedPreferenceDataSource
import zupzup.manager.data.service.SignInService
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val sharedPreferenceDataSource: SharedPreferenceDataSource,
    @ServiceModule.ZupZupServiceObjectNoneOkHttp private val signInService: SignInService
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request();
        val response = chain.proceed(request);
        Log.d("TAG", "Auth 인터셉터 작동")

        when (response.code) {
            401 -> {
                Log.d("TAG", "refresh token 재발급 시도")
                runBlocking {
                    try {
                        val refreshResponse =
                            signInService.signInRefresh(sharedPreferenceDataSource.getRefreshToken())
                        if (refreshResponse.isSuccessful) {
                            Log.d("TAG", "refresh token 재발급 완료")
                            with(refreshResponse.body()!!) {
                                sharedPreferenceDataSource.insertAccessToken(this.accessToken)
                            }
                        } else {
                            Log.d("TAG", "refresh token 재발급 실패1")
                        }
                    } catch (e: Exception) {
                        Log.d("TAG", "refresh token 재발급 실패2")
                        Log.d("TAG", e.toString())
                    }
                }
            }
        }

        return response
    }
}