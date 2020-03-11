package com.parentAps.di

import android.app.Application
import android.util.Log
import com.google.gson.Gson
import com.parentAps.BuildConfig.ENDPOINT
import com.parentAps.R
import com.parentAps.api.WeatherResponse.City
import com.parentAps.api.WeatherService
import com.parentAps.data.AppDatabase
import com.parentAps.data.extensions.getRawTextFile
import com.parentAps.ui.main.data.WeatherRemoteDataSource
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, CoreDataModule::class])
class AppModule {

    private fun createRetrofit(
        okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ENDPOINT)
            .client(okhttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    private fun <T> provideService(
        okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory, clazz: Class<T>
    ): T {
        return createRetrofit(okhttpClient, converterFactory).create(clazz)
    }

    @Singleton
    @Provides
    fun provideWeatherService(
        okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ) = provideService(okhttpClient, converterFactory, WeatherService::class.java)

    @Singleton
    @Provides
    fun provideWeatherSetRemoteDataSource(weatherService: WeatherService) =
        WeatherRemoteDataSource(weatherService)

    @Singleton
    @Provides
    fun provideDb(app: Application) = AppDatabase.getInstance(app)

    @Singleton
    @Provides
    fun provideWeatherSetDao(db: AppDatabase) = db.weatherDao()

    @Singleton
    @Provides
    fun provideCityList(app: Application): List<City> {
        val txtFile = app.resources?.getRawTextFile(R.raw.city)
        return Gson().fromJson(txtFile, Array<City>::class.java).asList()
    }

    @CoroutineScropeIO
    @Provides
    fun provideCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)
}