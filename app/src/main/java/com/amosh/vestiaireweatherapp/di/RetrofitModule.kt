package com.amosh.vestiaireweatherapp.di

import android.util.Log
import com.amosh.vestiaireweatherapp.BuildConfig
import com.amosh.vestiaireweatherapp.models.RemoteDataSource
import com.amosh.vestiaireweatherapp.utils.Constants.TIMEOUT_DURATION
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): Interceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BASIC)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: Interceptor,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.readTimeout(TIMEOUT_DURATION.toLong(), TimeUnit.MILLISECONDS)
        builder.connectTimeout(TIMEOUT_DURATION.toLong(), TimeUnit.MILLISECONDS)
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(httpLoggingInterceptor)
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(BuildConfig.HOST)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        retrofit: Retrofit
    ): RemoteDataSource {
        return retrofit.create(RemoteDataSource::class.java)
    }
}