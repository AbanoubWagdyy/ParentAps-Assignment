package com.parentAps.ui.homepage.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.parentAps.di.injectViewModel
import javax.inject.Inject

class WeatherActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: WeatherViewModel

    private val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION

    private val REQUEST_LOCATION_PERMISSION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = injectViewModel(viewModelFactory)
        super.onCreate(savedInstanceState)

        if (!checkLocationPermission()) {
            requestLocationPermission()
        }
    }

    private fun checkLocationPermission(): Boolean {
        return (ContextCompat.checkSelfPermission(
            applicationContext,
            locationPermission
        ) == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, locationPermission)) {
            AlertDialog.Builder(applicationContext)
                .setMessage("Need location permission to get current place")
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(arrayOf(locationPermission), REQUEST_LOCATION_PERMISSION)
                    }
                }
                .setNegativeButton(android.R.string.cancel, null)
                .show()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(locationPermission), REQUEST_LOCATION_PERMISSION)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                val isNeverAskAgain = !ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    locationPermission
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
                }
            }
        }
    }
}