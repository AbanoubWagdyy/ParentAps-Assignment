package com.parentAps.di

import androidx.lifecycle.ViewModel
import com.parentAps.ui.homepage.ui.HomepageViewModel

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomepageViewModel::class)
    abstract fun bindHomepageViewModel(viewModel: HomepageViewModel): ViewModel
}
