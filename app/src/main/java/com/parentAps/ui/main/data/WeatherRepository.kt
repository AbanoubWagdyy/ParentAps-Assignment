package com.parentAps.ui.main.data

import androidx.lifecycle.LiveData
import com.parentAps.data.Result
import com.parentAps.data.resultLiveData
import com.parentAps.ui.main.data.model.Weather
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

    fun getWeather(city: String): LiveData<Result<List<Weather>>> {
        return resultLiveData(
            databaseQuery = { dao.getCityWeather(city) },
            networkCall = { remoteSource.getWeather(city) },
            saveCallResult = { dao.insert(it) })
    }

    fun saveWeatherForDetails(weatherList: List<Weather>?) {

    }
}