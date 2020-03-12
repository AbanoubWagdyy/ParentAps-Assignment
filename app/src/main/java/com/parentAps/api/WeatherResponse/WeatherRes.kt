package com.parentAps.api.WeatherResponse

import com.google.gson.Gson
import com.parentAps.ui.data.model.Weather
import com.parentAps.ui.data.model.WeatherInfo

data class WeatherRes(
    val city: City?,
    val cnt: Int,
    val cod: String,
    val list: List<WeatherSpecs>,
    val message: Int
) {

    fun toDatabaseWeather(): Weather? {
        this.city?.let { it1 ->
            val weather = Weather()
            weather.cityId = it1.id.toString()
            weather.cityName = it1.name
            weather.country = it1.country
            weather.latitude = it1.coord?.lat
            weather.longitude = it1.coord?.lon
            val weatherInfoList = arrayListOf<WeatherInfo>()
            for (weatherInfoItem in this.list) {
                val weatherInfo = WeatherInfo()
                weatherInfo.dtTxt = weatherInfoItem.dt_txt
                weatherInfo.weatherDescription = weatherInfoItem.weather[0].description
                weatherInfo.weatherIcon = weatherInfoItem.weather[0].icon
                weatherInfo.weatherMain = weatherInfoItem.weather[0].main
                weatherInfo.windDeg = weatherInfoItem.wind.deg
                weatherInfo.windSpeed = weatherInfoItem.wind.speed
                weatherInfoList.add(weatherInfo)
            }
            weather.weatherInfoList = Gson().toJson(weatherInfoList)
            return weather
        }
        return null
    }
}