package com.amosh.vestiaireweatherapp.di

import android.content.Context
import com.amosh.vestiaireweatherapp.models.RemoteDataSource
import com.amosh.vestiaireweatherapp.repository.ForecastRepository
import com.amosh.vestiaireweatherapp.roomDb.ForecastsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideForecastRepository(
        @ApplicationContext context: Context,
        forecastDao: ForecastsDao,
        retrofitClient: RemoteDataSource,
    ): ForecastRepository {
        return ForecastRepository(
            forecastDao,
            retrofitClient,
            context
        )
    }
}