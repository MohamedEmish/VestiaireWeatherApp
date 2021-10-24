package com.amosh.vestiaireweatherapp.models.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.amosh.vestiaireweatherapp.utils.Constants
import java.io.Serializable
import kotlin.math.roundToInt

data class ForecastResponse(
    val city: City? = null,
    val cod: String? = null,
    val message: String? = null,
    val cnt: Int? = null,
    val list: List<ForecastData>? = null
) : Serializable

data class City(
    val id: Long? = null,
    val name: String? = null,
    val coord: Location? = null,
    val country: String? = null,
    val population: Long? = null,
    val timezone: Int? = null
) : Serializable

data class Location(
    val lon: Double? = null,
    val lat: Double? = null
) : Serializable

@Entity(tableName = Constants.Forecasts_TABLE_NAME)
data class ForecastData(
    @PrimaryKey
    val dt: Long? = null,
    val sunrise: Long? = null,
    val sunset: Long? = null,
    val temp: Temperature? = null,
    val feels_like: FeelsLike? = null,
    val pressure: Double? = null,
    val humidity: Int? = null,
    val weather: List<Weather>? = null,
    val speed: Double? = null,
    val deg: Int? = null,
    val gust: Double? = null,
    val clouds: Int? = null,
    val pop: Double? = null
) : Serializable

data class Temperature(
    val day: Double? = null,
    val min: Double? = null,
    val max: Double? = null,
    val night: Double? = null,
    val eve: Double? = null,
    val morn: Double? = null
) : Serializable {
    val avgTemp get() = (((min ?: 0.0) + (max ?: 0.0)) / 2).roundToInt()
}

data class FeelsLike(
    val day: Double? = null,
    val night: Double? = null,
    val eve: Double? = null,
    val morn: Double? = null
) : Serializable {
    val avgFeel get() = (((day ?: 0.0) + (night ?: 0.0) + (eve ?: 0.0) + (morn ?: 0.0)) / 2).roundToInt()
}

data class Weather(
    val id: Int? = null,
    val main: String? = null,
    val description: String? = null,
    val icon: String? = null
) : Serializable