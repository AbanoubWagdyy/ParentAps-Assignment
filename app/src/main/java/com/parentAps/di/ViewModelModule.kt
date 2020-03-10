package com.parentAps.di

import androidx.lifecycle.ViewModel
import com.parentAps.ui.homepage.ui.WeatherViewModel

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel::class)
    abstract fun bindHomepageViewModel(viewModel: WeatherViewModel): ViewModel
}
