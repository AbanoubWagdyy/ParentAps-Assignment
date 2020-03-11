package com.parentAps.api.WeatherResponse

data class WeatherRes(
    val city: City?,
    val cnt: Int,
    val cod: String,
    val list: List<WeatherSpecs>,
    val message: Int
)