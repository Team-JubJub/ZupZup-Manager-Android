package com.example.zupzup_manager.di

import com.example.zupzup_manager.data.datasource.admin.SharedPreferenceDataSource
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

        return chain.proceed(request)
    }
}