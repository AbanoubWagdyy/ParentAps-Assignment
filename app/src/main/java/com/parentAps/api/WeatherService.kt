package com.parentAps.api

import com.parentAps.ui.homepage.data.model.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather")
    suspend fun getWeather(@Query("q") search: String? = null,
                           @Query("appid") appid: String? = "b6907d289e10d714a6e88b30761fae22"): Response<Weather>
}