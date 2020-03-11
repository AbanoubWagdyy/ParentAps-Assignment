package com.parentAps.ui.weatherDetails

import androidx.lifecycle.ViewModel
import com.parentAps.ui.main.data.WeatherRepository
import javax.inject.Inject

class WeatherDetailsViewModel @Inject constructor(val mRepository: WeatherRepository) :
    ViewModel() {

}