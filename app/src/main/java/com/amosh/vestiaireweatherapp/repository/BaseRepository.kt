package com.amosh.vestiaireweatherapp.repository

import android.content.Context
import com.amosh.vestiaireweatherapp.R
import com.amosh.vestiaireweatherapp.models.appModels.AppResponseState
import com.amosh.vestiaireweatherapp.models.response.ForecastResponse
import com.amosh.vestiaireweatherapp.utils.isOffline
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class BaseRepository(val c: Context) {
    fun <T> handleNetworkResponse(response: Response<T>): AppResponseState<T?> {
        return if (response.isSuccessful) {
            AppResponseState.Success(response.body())
        } else parseNetworkErrorResponse(response)
    }

    private fun <T> parseNetworkErrorResponse(response: Response<T>): AppResponseState<T> {
        return AppResponseState.Error(
            parseNetworkErrorMessage(response.errorBody()).message ?: c.getString(R.string.error_invalid_server_data),
        )
    }

    private fun parseNetworkErrorMessage(errorBody: ResponseBody?): ForecastResponse =
        Gson().getAdapter(ForecastResponse::class.java).fromJson(errorBody?.string())

    fun <T> getNonNetworkError(e: Throwable): AppResponseState.Error<T> {
        return if (c.isOffline())
            AppResponseState.Error(
                c.getString(
                    R.string.no_internet_connection,
                )
            )
        else
            when (e) {
                is IOException ->
                    AppResponseState.Error(
                        c.getString(
                            R.string.error_internal_server_error,
                        )
                    )
                is HttpException ->
                    AppResponseState.Error(
                        e.message()
                    )
                is SocketTimeoutException, is ConnectException, is UnknownHostException ->
                    AppResponseState.Error(
                        c.getString(
                            R.string.error_server_connect_error,
                        )
                    )
                is JsonSyntaxException ->
                    AppResponseState.Error(
                        c.getString(
                            R.string.error_invalid_server_data,
                        )
                    )
                else ->
                    AppResponseState.Error(
                        c.getString(
                            R.string.error_internal_server_error,
                        )
                    )
            }
    }
}