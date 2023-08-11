package com.example.zupzup_manager.di

import com.example.zupzup_manager.data.datasource.admin.SharedPreferenceDataSource
import com.example.zupzup_manager.data.datasource.admin.SharedPreferenceDataSourceImpl
import com.example.zupzup_manager.data.datasource.admin.SignInDataSource
import com.example.zupzup_manager.data.datasource.admin.SignInDataSourceImpl
import com.example.zupzup_manager.data.datasource.item.ItemDataSource
import com.example.zupzup_manager.data.datasource.item.ItemDataSourceImpl
import com.example.zupzup_manager.data.datasource.lunasoft.LunaSoftDataSource
import com.example.zupzup_manager.data.datasource.lunasoft.LunaSoftDateSourceImpl
import com.example.zupzup_manager.data.datasource.order.OrderDataSource
import com.example.zupzup_manager.data.datasource.order.OrderDataSourceImpl
import com.example.zupzup_manager.data.datasource.store.StoreDataSource
import com.example.zupzup_manager.data.datasource.store.StoreDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindStoreDataSource(
        storeDataSourceImpl: StoreDataSourceImpl
    ): StoreDataSource

    @Binds
    @Singleton
    abstract fun bindItemDataSource(
        itemDataSourceImpl: ItemDataSourceImpl
    ): ItemDataSource

    @Binds
    @Singleton
    abstract fun bindOrderDataSource(
        orderRemoteDataSourceImpl: OrderDataSourceImpl
    ): OrderDataSource

    @Binds
    @Singleton
    abstract fun bindLunaSoftDataSource(
        lunaSoftDateSourceImpl: LunaSoftDateSourceImpl
    ): LunaSoftDataSource

    @Binds
    @Singleton
    abstract fun bindSignInDataSource(
        signInDataSourceImpl: SignInDataSourceImpl
    ): SignInDataSource

    @Binds
    @Singleton
    abstract fun bindSharedPreferenceDataSource(
        sharedPreferenceDataSourceImpl: SharedPreferenceDataSourceImpl
    ): SharedPreferenceDataSource
}