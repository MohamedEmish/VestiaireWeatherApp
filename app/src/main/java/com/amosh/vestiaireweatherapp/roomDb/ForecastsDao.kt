package com.amosh.vestiaireweatherapp.roomDb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amosh.vestiaireweatherapp.models.response.ForecastData
import com.amosh.vestiaireweatherapp.utils.Constants.Forecasts_TABLE_NAME

@Dao
interface ForecastsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(forecast: ForecastData): Long

    @Query("SELECT * FROM $Forecasts_TABLE_NAME ORDER BY dt")
    suspend fun get(): List<ForecastData>
}