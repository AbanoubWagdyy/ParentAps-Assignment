package com.parentAps.api

import com.parentAps.ui.main.data.model.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("weather")
    suspend fun getWeather(@Query("q") search: String? = null,
                           @Query("appid") appid: String? = "6850fd241da4505a67e28c81a2a116df"): Response<Weather>
}