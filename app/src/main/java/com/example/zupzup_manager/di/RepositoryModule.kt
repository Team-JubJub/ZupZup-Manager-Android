package com.example.zupzup_manager.di

import com.example.zupzup_manager.data.repository.SignInRepositoryImpl
import com.example.zupzup_manager.data.repository.LunaSoftRepositoryImpl
import com.example.zupzup_manager.data.repository.OrderRepositoryImpl
import com.example.zupzup_manager.data.repository.StoreRepositoryImpl
import com.example.zupzup_manager.domain.repository.SignInRepository
import com.example.zupzup_manager.domain.repository.LunaSoftRepository
import com.example.zupzup_manager.domain.repository.OrderRepository
import com.example.zupzup_manager.domain.repository.StoreRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindStoreRepository(
        storeRepositoryImpl: StoreRepositoryImpl
    ): StoreRepository

    @Binds
    @ViewModelScoped
    abstract fun bindOrderRepository(
        orderRepositoryImpl: OrderRepositoryImpl
    ): OrderRepository

    @Binds
    @ViewModelScoped
    abstract fun bindLunaSoftRepository(
        lunaSoftRepositoryImpl: LunaSoftRepositoryImpl
    ): LunaSoftRepository

    @Binds
    @ViewModelScoped
    abstract fun bindAdminRepository(
        signInRepositoryImpl : SignInRepositoryImpl
    ): SignInRepository
}
