package com.example.zupzup_manager.di

import com.example.zupzup_manager.data.repository.LunaSoftRepositoryImpl
import com.example.zupzup_manager.data.repository.ReservationRepositoryImpl
import com.example.zupzup_manager.data.repository.StoreRepositoryImpl
import com.example.zupzup_manager.domain.repository.LunaSoftRepository
import com.example.zupzup_manager.domain.repository.ReservationRepository
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
    abstract fun bindReservationRepository(
        reservationRepositoryImpl: ReservationRepositoryImpl
    ): ReservationRepository

    @Binds
    @ViewModelScoped
    abstract fun bindLunaSoftRepository(
        lunaSoftRepositoryImpl: LunaSoftRepositoryImpl
    ): LunaSoftRepository
}
