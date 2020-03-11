package com.parentAps.ui.main.data

import androidx.lifecycle.LiveData
import com.parentAps.data.Result
import com.parentAps.data.resultLiveData
import com.parentAps.ui.main.data.model.Weather
import com.parentAps.ui.main.data.model.WeatherInfo
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository module for handling data operations.
 */
@Singleton
class WeatherRepository @Inject constructor(
    private val dao: WeatherDao,
    private val remoteSource: WeatherRemoteDataSource
) {

    fun getWeather(cityId: String): LiveData<Result<List<Weather>>> {
        return resultLiveData(
            databaseQuery = { dao.getCityWeather(cityId) },
            networkCall = { remoteSource.getWeather(cityId) },
            saveCallResult = {
                val weather = Weather()
                weather.city_id = it.city?.id.toString()
                weather.city_name = it.city?.name
                weather.country = it.city?.country
                weather.latitude = it.city?.coord?.lat
                weather.longitude = it.city?.coord?.lon
                val weatherInfoList = arrayListOf<WeatherInfo>()
                for (weatherInfoItem in it.list) {
                    val weatherInfo = WeatherInfo()
                    weatherInfo.dt_txt = weatherInfoItem.dt_txt
                    weatherInfo.weatherDescription = weatherInfoItem.weather[0].description
                    weatherInfo.weatherIcon = weatherInfoItem.weather[0].icon
                    weatherInfo.weatherMain = weatherInfoItem.weather[0].main
                    weatherInfo.windDeg = weatherInfoItem.wind.deg
                    weatherInfo.windSpeed = weatherInfoItem.wind.speed
                    weatherInfoList.add(weatherInfo)
                }
                weather.weatherInfo = weatherInfoList
                dao.insert(weather)
            })
    }
}