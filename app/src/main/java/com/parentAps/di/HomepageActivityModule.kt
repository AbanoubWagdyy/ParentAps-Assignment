package com.parentAps.di


import com.parentAps.ui.homepage.ui.WeatherActivity
import dagger.Module

@Suppress("unused")
@Module
abstract class HomepageActivityModule {
    abstract fun contributeHomepageActivity(): WeatherActivity
}