package zupzup.manager.di

import zupzup.manager.data.datasource.admin.SharedPreferenceDataSource
import zupzup.manager.data.datasource.admin.SharedPreferenceDataSourceImpl
import zupzup.manager.data.datasource.admin.SignInDataSource
import zupzup.manager.data.datasource.admin.SignInDataSourceImpl
import zupzup.manager.data.datasource.item.ItemDataSource
import zupzup.manager.data.datasource.item.ItemDataSourceImpl
import zupzup.manager.data.datasource.lunasoft.LunaSoftDataSource
import zupzup.manager.data.datasource.lunasoft.LunaSoftDateSourceImpl
import zupzup.manager.data.datasource.order.OrderDataSource
import zupzup.manager.data.datasource.order.OrderDataSourceImpl
import zupzup.manager.data.datasource.store.StoreDataSource
import zupzup.manager.data.datasource.store.StoreDataSourceImpl
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