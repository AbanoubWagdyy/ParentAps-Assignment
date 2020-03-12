package com.parentAps.ui.main.ui


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.parentAps.api.WeatherResponse.City
import com.parentAps.data.Result
import com.parentAps.ui.main.data.WeatherRepository
import com.parentAps.ui.main.data.model.Weather
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
    val mRepository: WeatherRepository,
    val cityList: List<City>
) : ViewModel() {

    fun getWeather(city: String): LiveData<Result<List<Weather>>>? {
        val selectedCity = cityList.filter {
            it.name == city
        }
        return if (selectedCity.isNotEmpty()) {
            mRepository.getWeather(selectedCity[0].id.toString())
        } else {
            null
        }
    }

    fun getCityList(): ArrayList<String> {
        val cityNames = arrayListOf<String>()
        cityList.forEach {
            cityNames.add(it.name)
        }
        return cityNames
    }

    fun saveCity(cityId: String) {
        mRepository.saveCityId(cityId.toInt())
    }

    fun getSavedCity(): Weather? {
        return mRepository.getSavedWeatherCity().value?.get(0)
    }
}