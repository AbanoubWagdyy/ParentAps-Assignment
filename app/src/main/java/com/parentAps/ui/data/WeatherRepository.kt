package com.parentAps.ui.data

import androidx.lifecycle.LiveData
import com.parentAps.api.WeatherResponse.City
import com.parentAps.data.Result
import com.parentAps.data.resultLiveData
import com.parentAps.ui.data.model.Weather
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

    private var selectedCityId: Int = 0

    fun getWeather(cityId: String): LiveData<Result<List<Weather>>> {
        return resultLiveData(
            databaseQuery = { dao.getCityWeather(cityId) },
            networkCall = { remoteSource.getWeather(cityId) },
            saveCallResult = {
                val weather = it.toDatabaseWeather()
                if (weather != null) {
                    val id = dao.insert(weather)
                    if (id != 0L) {
                        return@resultLiveData
                    }
                }
            })
    }

    fun saveCityId(selectedCityId: Int) {
        this.selectedCityId = selectedCityId
    }

    fun getSavedWeatherCity(): LiveData<List<Weather>> {
        return dao.getCityWeather(selectedCityId.toString())
    }

    fun deleteSavedCityWeather() {
        val weather = dao.getCityWeatherForDelete(selectedCityId.toString())
        if (weather.isNotEmpty())
            dao.delete(weather[0])
    }
}