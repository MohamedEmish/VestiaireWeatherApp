package com.amosh.vestiaireweatherapp.models.appModels

import android.graphics.pdf.PdfDocument

sealed class AppResponseState<out R> {
    data class Success<out T>(val data: T?) : AppResponseState<T>()
    data class CachedData<out T>(val data: T?): AppResponseState<T>()
    data class Error<T>(val message: String) : AppResponseState<T>()
    object Loading : AppResponseState<Nothing>()
}