package com.parentAps.ui.main.data

import androidx.lifecycle.LiveData
import com.google.gson.Gson
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

    private var selectedCityId: Int = 0

    fun getWeather(cityId: String): LiveData<Result<List<Weather>>> {
        return resultLiveData(
            databaseQuery = { dao.getCityWeather(cityId) },
            networkCall = { remoteSource.getWeather(cityId) },
            saveCallResult = {
                it.city?.let { it1 ->
                    val weather = Weather()
                    weather.cityId = it1.id.toString()
                    weather.cityName = it1.name
                    weather.country = it1.country
                    weather.latitude = it1.coord?.lat
                    weather.longitude = it1.coord?.lon
                    val weatherInfoList = arrayListOf<WeatherInfo>()
                    for (weatherInfoItem in it.list) {
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
                    dao.insert(weather)
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