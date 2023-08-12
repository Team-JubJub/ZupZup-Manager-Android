package zupzup.manager.di

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val lunaSoftBaseUrl = "https://jupiter.lunasoft.co.kr/"
    private const val manageBaseUrl = "https://zupzuptest.com:8080/"

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class LunaSoftRetrofitObject

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ZupZupRetrofitObject

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ZupZupRetrofitObjectNoneInterceptor

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class ZupZupRetrofitObjectNoneOkHttp

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class OkHttpClientWithInterceptor

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class OkHttpClientNoneInterceptor

    @Provides
    @Singleton
    @LunaSoftRetrofitObject
    fun provideLunaSoftRetrofitObject(
        @OkHttpClientWithInterceptor client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(lunaSoftBaseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    // 얘를 OkHttpClientNoneInterceptor 가진 걸로 하나 더 만들고 signinservice에서 사용할 것
    @Provides
    @Singleton
    @ZupZupRetrofitObject
    fun provideZupZupRetrofitObject(
        @OkHttpClientWithInterceptor client: OkHttpClient
    ): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder()
            .baseUrl(manageBaseUrl)
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    // 얘는 dependency cycle 방지용 위랑 같이 묶어두기 OkHttp 객체 자체가 안 들어감
    @Provides
    @Singleton
    @ZupZupRetrofitObjectNoneOkHttp
    fun provideZupZupRetrofitObjectNoneOkHttp(): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder()
            .baseUrl(manageBaseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    // SignInService(헤더 넣는 애, 안 넣는 애 다 있으므로) 전용 Retrofit 객체
    @Provides
    @Singleton
    @ZupZupRetrofitObjectNoneInterceptor
    fun provideZupZupRetrofitObjectNoneInterceptor(
        @OkHttpClientNoneInterceptor client: OkHttpClient
    ): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder()
            .baseUrl(manageBaseUrl)
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    @OkHttpClientWithInterceptor
    fun provideOkHttpClientWithInterceptor(
        authInterceptor: AuthInterceptor,
        headerInterceptor: HeaderInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .addInterceptor(headerInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    @OkHttpClientNoneInterceptor
    fun provideOkHttpClientNoneInterceptor(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }
}