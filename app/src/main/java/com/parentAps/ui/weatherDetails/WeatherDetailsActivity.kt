package com.parentAps.ui.weatherDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.parentAps.R
import com.parentAps.di.injectViewModel
import com.parentAps.ui.main.ui.WeatherViewModel
import javax.inject.Inject

class WeatherDetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_details)
        if (::viewModelFactory.isInitialized) {
            viewModel = injectViewModel(viewModelFactory)
        }
    }
}