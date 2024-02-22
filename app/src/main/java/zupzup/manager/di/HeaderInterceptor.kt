package zupzup.manager.di

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response
import zupzup.manager.data.datasource.admin.SharedPreferenceDataSource
import javax.inject.Inject

class HeaderInterceptor @Inject constructor(
    private val sharedPreferenceDataSource: SharedPreferenceDataSource
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d("TAG", "Header intercept: ")
        val request = chain.request().newBuilder()
            .addHeader("accessToken", sharedPreferenceDataSource.getAccessToken())
            .apply {
                Log.d("TAG", "apply : ${chain.request().url}")
                if (isNeedBothAccessRefreshRequest(chain.request().url.toString())) {
                    addHeader(
                        "refreshToken",
                        sharedPreferenceDataSource.getRefreshToken()
                    )
                }
            }.build()


//        Log.d("TAG", "Header 인터셉터 작동")
        return chain.proceed(request)
    }

    private fun isNeedBothAccessRefreshRequest(url: String): Boolean {
        return when (url) {
            "${Constants.testBaseUrl}mobile/sign-out" -> true
            else -> false
        }
    }
}