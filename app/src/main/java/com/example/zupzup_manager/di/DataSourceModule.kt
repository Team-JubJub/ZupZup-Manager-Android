package com.example.zupzup_manager.di

import com.example.zupzup_manager.data.datasource.admin.AdminDataSource
import com.example.zupzup_manager.data.datasource.admin.AdminDataSourceImpl
import com.example.zupzup_manager.data.datasource.admin.SharedPreferenceDataSource
import com.example.zupzup_manager.data.datasource.admin.SharedPreferenceDataSourceImpl
import com.example.zupzup_manager.data.datasource.lunasoft.LunaSoftDataSource
import com.example.zupzup_manager.data.datasource.lunasoft.LunaSoftDateSourceImpl
import com.example.zupzup_manager.data.datasource.reservation.ReservationDataSource
import com.example.zupzup_manager.data.datasource.reservation.ReservationDataSourceImpl
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

    @Binds
    @Singleton
    abstract fun bindLunaSoftDataSource(
        lunaSoftDateSourceImpl: LunaSoftDateSourceImpl
    ): LunaSoftDataSource

    @Binds
    @Singleton
    abstract fun bindAdminDataSource(
        adminDataSourceImpl: AdminDataSourceImpl
    ): AdminDataSource

    @Binds
    @Singleton
    abstract fun bindSharedPreferenceDataSource(
        sharedPreferenceDataSourceImpl: SharedPreferenceDataSourceImpl
    ): SharedPreferenceDataSource
}