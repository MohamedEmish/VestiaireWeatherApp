package com.amosh.vestiaireweatherapp.repository

import android.content.Context
import com.amosh.vestiaireweatherapp.models.RemoteDataSource
import com.amosh.vestiaireweatherapp.models.appModels.AppResponseState
import com.amosh.vestiaireweatherapp.models.response.ForecastResponse
import com.amosh.vestiaireweatherapp.roomDb.ForecastsDao
import com.amosh.vestiaireweatherapp.utils.isOffline
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*

class ForecastRepository
constructor(
    private val forecastDao: ForecastsDao,
    private val remoteDataSource: RemoteDataSource,
    @ApplicationContext val context: Context
) : BaseRepository(context) {
    suspend fun getForecast(): AppResponseState<ForecastResponse?> {
        return try {
            val networkResponse =
                remoteDataSource.getForecast()

            if (networkResponse.isSuccessful) {
                networkResponse.body()?.list?.forEach {
                    forecastDao.insert(it)
                }
            }
            handleNetworkResponse(networkResponse)

        } catch (e: Exception) {
            if (c.isOffline()) {
                AppResponseState.CachedData(
                    ForecastResponse(
                        list = forecastDao.get()
                            .filter { !Date(it.dt?.times(1000) ?: Date().time).before(Date()) }
                    )
                )
            } else getNonNetworkError(e)
        }
    }
}