package com.parentAps.api

import com.parentAps.api.WeatherResponse.WeatherRes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("forecast?&cnt=5")
    suspend fun getWeather(
        @Query("id") city_id: String? = null,
        @Query("appid") appid: String? = "6850fd241da4505a67e28c81a2a116df"
    ): Response<WeatherRes>
}