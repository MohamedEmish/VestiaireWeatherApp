package com.amosh.vestiaireweatherapp.di

import android.content.Context
import androidx.room.Room
import com.amosh.vestiaireweatherapp.roomDb.ForecastsDao
import com.amosh.vestiaireweatherapp.roomDb.ForecastsDatabase
import com.amosh.vestiaireweatherapp.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideForecastsDatabaseDB(@ApplicationContext context: Context): ForecastsDatabase {
        return Room.databaseBuilder(
            context,
            ForecastsDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }


    @Singleton
    @Provides
    fun provideForecastsDao(db: ForecastsDatabase): ForecastsDao {
        return db.forecastsDao()
    }
}