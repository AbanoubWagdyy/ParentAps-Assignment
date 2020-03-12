package com.parentAps.ui.main.data

import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.parentAps.api.WeatherResponse.WeatherRes
import com.parentAps.data.Result
import com.parentAps.data.resultLiveData
import com.parentAps.ui.main.data.model.Weather
import com.parentAps.ui.main.data.model.WeatherInfo
import retrofit2.Response
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
}