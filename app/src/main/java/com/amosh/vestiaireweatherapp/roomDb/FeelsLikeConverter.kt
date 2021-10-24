package com.amosh.vestiaireweatherapp.roomDb

import androidx.room.TypeConverter
import com.amosh.vestiaireweatherapp.models.response.FeelsLike
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class FeelsLikeConverter {
    @TypeConverter
    fun fromTemperature(value: FeelsLike): String {
        val gson = Gson()
        val type = object : TypeToken<FeelsLike>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toTemperature(value: String): FeelsLike {
        val gson = Gson()
        val type = object : TypeToken<FeelsLike>() {}.type
        return gson.fromJson(value, type)
    }
}