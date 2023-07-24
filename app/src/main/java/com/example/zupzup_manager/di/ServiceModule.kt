package com.example.zupzup_manager.di

import com.example.zupzup_manager.data.service.LunaSoftService
import com.example.zupzup_manager.data.service.MerchandiseService
import com.example.zupzup_manager.data.service.OrderService
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
        @NetworkModule.ZupZupRetrofitObject retrofit: Retrofit
    ): SignInService {
        return retrofit.create(SignInService::class.java)
    }

    @Provides
    @Singleton
    fun providesOrderService(
        @NetworkModule.ZupZupRetrofitObject retrofit: Retrofit
    ): OrderService {
        return retrofit.create(OrderService::class.java)
    }

    @Provides
    @Singleton
    fun providesMerchandiseService(
        @NetworkModule.ZupZupRetrofitObject retrofit: Retrofit
    ): MerchandiseService {
        return retrofit.create(MerchandiseService::class.java)
    }
}