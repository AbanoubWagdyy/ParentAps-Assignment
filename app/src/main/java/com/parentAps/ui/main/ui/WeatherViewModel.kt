package com.parentAps.ui.main.ui


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.parentAps.api.WeatherResponse.City
import com.parentAps.data.Result
import com.parentAps.ui.data.WeatherRepository
import com.parentAps.ui.data.model.Weather
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
    val mRepository: WeatherRepository,
    val cityList: List<City>
) : ViewModel() {

    fun getWeather(city: String): LiveData<Result<List<Weather>>>? {
        if (city.equals("")) {
            return null
        }
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

    fun getSavedCityWeather(): LiveData<List<Weather>> {
        return mRepository.getSavedWeatherCity()
    }

    fun deleteSavedCity() {
        mRepository.deleteSavedCityWeather()
    }
}