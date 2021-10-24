package com.amosh.vestiaireweatherapp.roomDb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.amosh.vestiaireweatherapp.models.response.ForecastData

@Database(entities = [ForecastData::class], version = 1)
@TypeConverters(TemperatureConverter::class, FeelsLikeConverter::class, WeatherConverter::class)
abstract class ForecastsDatabase : RoomDatabase() {
    abstract fun forecastsDao(): ForecastsDao
}