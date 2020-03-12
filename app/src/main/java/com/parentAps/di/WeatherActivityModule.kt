package com.parentAps.di

import com.parentAps.ui.main.ui.WeatherActivity
import com.parentAps.ui.weatherDetails.WeatherDetailsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class WeatherActivityModule {

    @ContributesAndroidInjector
    abstract fun contributeWeatherActivity(): WeatherActivity

    @ContributesAndroidInjector
    abstract fun contributeWeatherDetailsActivity(): WeatherDetailsActivity
}