package com.amosh.vestiaireweatherapp.models

import com.amosh.vestiaireweatherapp.BuildConfig
import com.amosh.vestiaireweatherapp.models.response.ForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RemoteDataSource {
    @GET("forecast/daily")
    suspend fun getForecast(
        @Query("APPID") appId: String = BuildConfig.API_KEY,
        @Query("cnt") count: Int = 16,
        @Query("units") units: String = "metric",
        @Query("q") country: String = "Paris"
    ): Response<ForecastResponse>
}