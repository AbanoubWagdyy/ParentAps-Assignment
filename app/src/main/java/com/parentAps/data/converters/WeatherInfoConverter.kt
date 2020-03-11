package com.parentAps.data.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.parentAps.ui.main.data.model.WeatherInfo

class WeatherInfoConverter {

    @TypeConverter
    fun fromWeatherInfoList(value: List<WeatherInfo>): String {
        val gson = Gson()
        val type = object : TypeToken<List<WeatherInfo>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toWeatherInfoList(value: String): List<WeatherInfo> {
        val gson = Gson()
        val type = object : TypeToken<List<WeatherInfo>>() {}.type
        return gson.fromJson(value, type)
    }
}