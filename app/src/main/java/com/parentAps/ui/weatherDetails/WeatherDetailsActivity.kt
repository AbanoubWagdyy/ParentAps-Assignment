package com.parentAps.ui.weatherDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.parentAps.R
import com.parentAps.di.Injectable
import com.parentAps.di.injectViewModel
import com.parentAps.ui.data.model.WeatherInfo
import com.parentAps.ui.main.ui.WeatherViewModel
import com.parentAps.ui.weatherDetails.adapter.WeatherAdapter
import kotlinx.android.synthetic.main.activity_weather_details.*
import javax.inject.Inject

class WeatherDetailsActivity : AppCompatActivity(), Injectable {

    private var isToRemoveFromDatabase: Boolean = true

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_details)

        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        if (::viewModelFactory.isInitialized) {
            viewModel = injectViewModel(viewModelFactory)
            viewModel.getSavedCityWeather().observe(this, Observer { weather ->
                val weatherType = object : TypeToken<List<WeatherInfo>>() {}.type
                val weatherInfoList =
                    Gson().fromJson<List<WeatherInfo>>(weather[0].weatherInfoList, weatherType)
                renderWeatherListInfo(weatherInfoList)
                initializeListener()
            })
        }
    }

    private fun initializeListener() {
        addToHomepage.setOnClickListener {
            isToRemoveFromDatabase = false
            finish()
        }
    }

    private fun renderWeatherListInfo(weatherInfoList: List<WeatherInfo>?) {
        rvWeatherInfo.layoutManager = LinearLayoutManager(this)
        rvWeatherInfo.setHasFixedSize(true)
        rvWeatherInfo.adapter = weatherInfoList?.let { WeatherAdapter(it) }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStop() {
        if (isToRemoveFromDatabase) {
            viewModel.deleteSavedCity()
        }
        super.onStop()
    }
}