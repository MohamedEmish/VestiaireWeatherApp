package com.amosh.vestiaireweatherapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amosh.vestiaireweatherapp.models.appModels.AppResponseState
import com.amosh.vestiaireweatherapp.models.response.ForecastResponse
import com.amosh.vestiaireweatherapp.repository.ForecastRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel
@Inject
constructor(
    private val repository: ForecastRepository
) : ViewModel() {

    private val _forecast: MutableLiveData<AppResponseState<ForecastResponse?>> = MutableLiveData()

    val forecast: LiveData<AppResponseState<ForecastResponse?>>
        get() = _forecast

    fun getForecasts() {
        viewModelScope.launch {
            _forecast.value = AppResponseState.Loading
            _forecast.value = repository.getForecast()
        }
    }
}
