package com.example.zupzup_manager.di

import com.example.zupzup_manager.data.datasource.admin.SharedPreferenceDataSource
import com.example.zupzup_manager.data.service.SignInService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class AuthInterceptor @Inject constructor(
    private val sharedPreferenceDataSource: SharedPreferenceDataSource,
    @NetworkModule.ZupZupRetrofitObjectNoneInterceptor private val signInService: SignInService
//    private val signInService: @JvmSuppressWildcards Lazy<SignInService>
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request();
        val response = chain.proceed(request);

        when (response.code) {
            401 -> {
                runBlocking {
                    val refreshResponse =
//                        signInService.signInRefresh(sharedPreferenceDataSource.getRefreshToken())
                        signInService.signInRefresh(sharedPreferenceDataSource.getRefreshToken())
                    if (refreshResponse.isSuccessful) {
                        with(refreshResponse.body()!!) {
                            sharedPreferenceDataSource.insertAccessToken(this.accessToken)
                        }
                    }
                }
            }
        }

        return response
    }
}