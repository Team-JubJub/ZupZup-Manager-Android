package zupzup.manager.di

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
    annotation class OrderRef

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class TestOrderRef

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
    @OrderRef
    fun provideFirebaseOrderRef(): CollectionReference {
        return Firebase.firestore.collection(Constants.orderRef)
    }

    @Singleton
    @Provides
    @TestOrderRef
    fun provideFirebaseTestOrderRef(): CollectionReference {
        return Firebase.firestore.collection(Constants.testOrderRef)
    }

    @Singleton
    @Provides
    @TestStoreRef
    fun provideFirebaseTestStoreRef(): CollectionReference {
        return Firebase.firestore.collection(Constants.testStoreRef)
    }

}