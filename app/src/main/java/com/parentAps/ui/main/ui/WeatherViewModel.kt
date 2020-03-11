package com.parentAps.ui.main.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.parentAps.data.Result
import com.parentAps.ui.main.data.WeatherRepository
import com.parentAps.ui.main.data.model.Weather
import javax.inject.Inject

class WeatherViewModel @Inject constructor(val mRepository: WeatherRepository) : ViewModel() {

    fun getWeather(city: String): LiveData<Result<List<Weather>>> {
        return mRepository.getWeather(city)
    }

    fun saveResult(data: List<Weather>?) {
        mRepository.saveWeatherForDetails(data)
    }
}