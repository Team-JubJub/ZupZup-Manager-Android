package com.example.zupzup_manager.di

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object FireBaseModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class StoreRef

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ReservationRef

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class AdminRef

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class TestReservationRef

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class TestStoreRef

    @Singleton
    @Provides
    @StoreRef
    fun provideFirebaseStoreRef(): CollectionReference {
        return Firebase.firestore.collection(Constants.storeRef)
    }

    @Singleton
    @Provides
    @ReservationRef
    fun provideFirebaseReservationRef(): CollectionReference {
        return Firebase.firestore.collection(Constants.reservationRef)
    }

    @Singleton
    @Provides
    @TestReservationRef
    fun provideFirebaseTestReservationRef(): CollectionReference {
        return Firebase.firestore.collection(Constants.testReservationRef)
    }

    @Singleton
    @Provides
    @TestStoreRef
    fun provideFirebaseTestStoreRef(): CollectionReference {
        return Firebase.firestore.collection(Constants.testStoreRef)
    }

    @Singleton
    @Provides
    @TestStoreRef
    fun provideFirebaseAdminRef(): CollectionReference {
        return Firebase.firestore.collection(Constants.adminRef)
    }
}