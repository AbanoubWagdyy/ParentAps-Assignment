package com.parentAps.ui.main.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.parentAps.R
import com.parentAps.di.Injectable
import com.parentAps.di.injectViewModel
import kotlinx.android.synthetic.main.activity_homepage.*
import javax.inject.Inject
import com.parentAps.data.Result
import com.parentAps.data.extensions.hide
import com.parentAps.data.extensions.show
import com.parentAps.ui.homepageWeatherList.HomepageWeatherListActivity
import com.parentAps.ui.weatherDetails.WeatherDetailsActivity

class WeatherActivity : AppCompatActivity(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: WeatherViewModel

    private val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private val REQUEST_LOCATION_PERMISSION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
        if (::viewModelFactory.isInitialized) {
            viewModel = injectViewModel(viewModelFactory)
            if (!checkLocationPermissions()) {
                requestLocationPermission()
            } else {
                initializeListeners()
            }
        }
    }

    private fun initializeListeners() {

        search.setOnClickListener {
            viewModel.getWeather(etSearch.text.toString()).observe(this, Observer { result ->
                when (result.status) {
                    Result.Status.SUCCESS -> {
                        viewModel.saveResult(result.data)
                        Log.d("result", result.data.toString())
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