package zupzup.manager.di

import android.util.Log
import zupzup.manager.data.datasource.admin.SharedPreferenceDataSource
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor(
    private val sharedPreferenceDataSource: SharedPreferenceDataSource
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("accessToken", sharedPreferenceDataSource.getAccessToken())
            .build()

        Log.d("TAG", "Header 인터셉터 작동")
        return chain.proceed(request)
    }
}