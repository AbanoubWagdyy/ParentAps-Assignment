package com.parentAps.ui.homepage.data

import com.parentAps.api.BaseDataSource
import com.parentAps.api.WeatherService
import javax.inject.Inject

class WeatherRemoteDataSource @Inject constructor(private val service: WeatherService) : BaseDataSource() {
    suspend fun getWeather(city: String) = getResult { service.getWeather(city) }
}
