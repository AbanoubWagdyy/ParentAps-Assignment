package com.parentAps.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.parentAps.ui.main.ui.WeatherViewModel

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(WeatherViewModel::class)
    abstract fun bindWeatherViewModel(viewModel: WeatherViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
