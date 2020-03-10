package com.parentAps.di


import com.parentAps.ui.homepage.ui.HomepageActivity
import dagger.Module

@Suppress("unused")
@Module
abstract class HomepageActivityModule {
    abstract fun contributeHomepageActivity(): HomepageActivity
}