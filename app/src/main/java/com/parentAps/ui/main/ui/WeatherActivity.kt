package com.parentAps.ui.main.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.parentAps.R
import com.parentAps.api.WeatherResponse.City
import com.parentAps.data.Result
import com.parentAps.data.extensions.hide
import com.parentAps.data.extensions.hideKeyboard
import com.parentAps.data.extensions.show
import com.parentAps.di.Injectable
import com.parentAps.di.injectViewModel
import com.parentAps.ui.homepageWeatherList.HomepageWeatherListActivity
import com.parentAps.ui.weatherDetails.WeatherDetailsActivity
import kotlinx.android.synthetic.main.activity_weather.*
import javax.inject.Inject


class WeatherActivity : AppCompatActivity(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var cityList: List<City>

    private lateinit var viewModel: WeatherViewModel

    private val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private val REQUEST_LOCATION_PERMISSION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        if (::viewModelFactory.isInitialized) {
            viewModel = injectViewModel(viewModelFactory)
            if (!checkLocationPermissions()) {
                requestLocationPermission()
            } else {
                initializeListeners()
                populateAutoCompleteLocation()
            }
        }
    }

    private fun populateAutoCompleteLocation() {
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_dropdown_item_1line, viewModel.getCityList()
        )
        autoCompleteCity.setAdapter(adapter)
    }

    private fun initializeListeners() {
        search.setOnClickListener {
            hideKeyboard()
            viewModel.getWeather(autoCompleteCity.text.toString())
                ?.observe(this, Observer { result ->
                    when (result.status) {
                        Result.Status.SUCCESS -> {
                            Log.d("result", result.data.toString())
                            viewModel.saveCity(autoCompleteCity.text.toString())
                            val intent =
                                Intent(this@WeatherActivity, WeatherDetailsActivity::class.java)
                            startActivity(intent)
                        }
                        Result.Status.SHOW_LOADING -> progressBar.show()
                        Result.Status.HIDE_LOADING -> progressBar.hide()
                        Result.Status.ERROR -> {
                            Log.d("Error", "Error")
                        }
                    }
                })
        }
        viewSavedWeather.setOnClickListener {
            val intent =
                Intent(this@WeatherActivity, HomepageWeatherListActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkLocationPermissions(): Boolean {
        for (permission in locationPermissions) {
            if (ContextCompat.checkSelfPermission(
                    applicationContext,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    private fun requestLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(locationPermissions, REQUEST_LOCATION_PERMISSION)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Permissions :", "granted")
                initializeListeners()
                populateAutoCompleteLocation()
            } else {
                for (permission in locationPermissions) {
                    val isNeverAskAgain = !ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        permission
                    )
                    if (isNeverAskAgain) {
                        Snackbar.make(
                                findViewById(android.R.id.content),
                                "Location Permission required",
                                Snackbar.LENGTH_LONG
                            )
                            .setAction("Settings") {
                                Intent(
                                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.parse("package:${packageName}")
                                ).apply {
                                    addCategory(Intent.CATEGORY_DEFAULT)
                                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                    startActivity(this)
                                }
                            }
                            .show()
                        return
                    }
                }
            }
        }
    }
}