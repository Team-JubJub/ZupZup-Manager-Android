package zupzup.manager.di

import zupzup.manager.data.repository.ItemRepositoryImpl
import zupzup.manager.data.repository.LunaSoftRepositoryImpl
import zupzup.manager.data.repository.OrderRepositoryImpl
import zupzup.manager.data.repository.SignInRepositoryImpl
import zupzup.manager.data.repository.StoreRepositoryImpl
import zupzup.manager.domain.repository.ItemRepository
import zupzup.manager.domain.repository.LunaSoftRepository
import zupzup.manager.domain.repository.OrderRepository
import zupzup.manager.domain.repository.SignInRepository
import zupzup.manager.domain.repository.StoreRepository
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
    abstract fun bindItemRepository(
        itemRepositoryImpl: ItemRepositoryImpl
    ): ItemRepository

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
