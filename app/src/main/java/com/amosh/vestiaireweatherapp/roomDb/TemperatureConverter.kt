package com.amosh.vestiaireweatherapp.roomDb

import androidx.room.TypeConverter
import com.amosh.vestiaireweatherapp.models.response.FeelsLike
import com.amosh.vestiaireweatherapp.models.response.Temperature
import com.amosh.vestiaireweatherapp.models.response.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TemperatureConverter {
    @TypeConverter
    fun fromTemperature(value: Temperature): String {
        val gson = Gson()
        val type = object : TypeToken<Temperature>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toTemperature(value: String): Temperature {
        val gson = Gson()
        val type = object : TypeToken<Temperature>() {}.type
        return gson.fromJson(value, type)
    }
}