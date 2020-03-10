package com.parentAps.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.parentAps.ui.homepage.data.model.Weather

class Converters {

    @TypeConverter
    fun WeatherToString(weather: Weather): String {
        val gson = Gson()
        return gson.toJson(weather)
    }

    @TypeConverter
    fun StringToWeather(string: String): Weather {
        val gson = Gson()
        return gson.fromJson(string, Weather::class.java)
    }
}