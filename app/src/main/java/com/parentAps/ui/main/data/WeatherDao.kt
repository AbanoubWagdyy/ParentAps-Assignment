package com.parentAps.ui.main.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.parentAps.ui.main.data.model.Weather

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather WHERE cityId = :cityId")
    fun getCityWeather(cityId: String): LiveData<List<Weather>>

    @Query("SELECT * FROM weather")
    fun getAllCityWeather(): LiveData<List<Weather>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weather: Weather): Long
}