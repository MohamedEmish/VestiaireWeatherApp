package com.amosh.vestiaireweatherapp.roomDb

import androidx.room.TypeConverter
import com.amosh.vestiaireweatherapp.models.response.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class WeatherConverter {
    @TypeConverter
    fun fromWeatherList(value: List<Weather>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Weather>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toWeatherList(value: String): List<Weather> {
        val gson = Gson()
        val type = object : TypeToken<List<Weather>>() {}.type
        return gson.fromJson(value, type)
    }
}