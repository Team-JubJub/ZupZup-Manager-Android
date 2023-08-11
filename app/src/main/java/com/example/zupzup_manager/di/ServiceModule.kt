package com.example.zupzup_manager.di

import com.example.zupzup_manager.data.service.LunaSoftService
import com.example.zupzup_manager.data.service.ItemService
import com.example.zupzup_manager.data.service.OrderService
import com.example.zupzup_manager.data.service.SignInService
import com.example.zupzup_manager.data.service.StoreService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {


    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ZupZupServiceObject

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ZupZupServiceObjectNoneOkHttp

    @Provides
    @Singleton
    fun provideLunaSoftService(
        @NetworkModule.LunaSoftRetrofitObject retrofit: Retrofit
    ): LunaSoftService {
        return retrofit.create(LunaSoftService::class.java)
    }

    @Provides
    @Singleton
    @ZupZupServiceObject
    fun provideSignInService(
        @NetworkModule.ZupZupRetrofitObjectNoneInterceptor retrofit: Retrofit
    ): SignInService {
        return retrofit.create(SignInService::class.java)
    }

    @Provides
    @Singleton
    @ZupZupServiceObjectNoneOkHttp
    fun provideSignInServiceNoneInterceptor(
        @NetworkModule.ZupZupRetrofitObjectNoneOkHttp retrofit: Retrofit
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
    fun providesItemService(
        @NetworkModule.ZupZupRetrofitObject retrofit: Retrofit
    ): ItemService {
        return retrofit.create(ItemService::class.java)
    }

    @Provides
    @Singleton
    fun providesStoreService(
        @NetworkModule.ZupZupRetrofitObject retrofit: Retrofit
    ): StoreService {
        return retrofit.create(StoreService::class.java)
    }}