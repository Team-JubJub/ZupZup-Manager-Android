package com.example.zupzup_manager.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val lunaSoftBaseUrl = "https://jupiter.lunasoft.co.kr/"
    private const val manageBaseUrl = "https://zupzuptest.com:8080/"

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class LunaSoftRetrofitObject

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ZupZupRetrofitObject

    @Provides
    @Singleton
    @LunaSoftRetrofitObject
    fun provideLunaSoftRetrofitObject(
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(lunaSoftBaseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @ZupZupRetrofitObject
    fun provideZupZupRetrofitObject(
        client: OkHttpClient
    ): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder()
            .baseUrl(manageBaseUrl)
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
//        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
//            .addInterceptor(authInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }
}