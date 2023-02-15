package com.example.zupzup_manager.di

import com.example.zupzup_manager.data.datasource.ReservationDataSource
import com.example.zupzup_manager.data.datasource.ReservationDataSourceImpl
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
    abstract fun bindReservationDataSource(
        reservationRemoteDataSourceImpl: ReservationDataSourceImpl
    ): ReservationDataSource
}