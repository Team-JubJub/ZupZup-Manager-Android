package com.example.zupzup_manager.di

import com.example.zupzup_manager.data.service.LunaSoftService
import com.example.zupzup_manager.data.service.SignInService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideLunaSoftService(
        @NetworkModule.LunaSoftRetrofitObject retrofit: Retrofit
    ): LunaSoftService {
        return retrofit.create(LunaSoftService::class.java)
    }

    @Provides
    @Singleton
    fun provideSignInService(
        @NetworkModule.SignInRetrofitObject retrofit: Retrofit
    ): SignInService {
        return retrofit.create(SignInService::class.java)
    }
}