package com.sliide.app.di

import com.sliide.app.BuildConfig
import com.sliide.app.data.UserAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val ACCEPT_HEADER = "Accept"
    private const val CONTENT_TYPE_HEADER = "Content-type"
    private const val AUTHORIZATION_HEADER = "Authorization"

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = getLoggingLevel()
        }

    @Singleton
    @Provides
    fun provideHttpHeaderInterceptor() = Interceptor { chain ->
        val headersBuilder = getHeadersBuilder(chain)
        chain.proceed(headersBuilder.build())
    }

    @Singleton
    @Provides
    fun providesOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideUserApi(
        okHttpClient: OkHttpClient
    ): UserAPI =
        Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserAPI::class.java)

    private fun getLoggingLevel() = when (BuildConfig.DEBUG) {
        true -> HttpLoggingInterceptor.Level.BODY
        false -> HttpLoggingInterceptor.Level.BASIC
    }

    private fun getHeadersBuilder(chain: Interceptor.Chain): Request.Builder {
        return chain
            .request()
            .newBuilder()
            .addHeader(ACCEPT_HEADER, "application/json")
            .addHeader(CONTENT_TYPE_HEADER, "application/json")
            .addHeader(AUTHORIZATION_HEADER, "Bearer ${BuildConfig.API_ACCESS_TOKEN}")
    }
}