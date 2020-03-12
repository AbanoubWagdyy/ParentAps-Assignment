package com.parentAps.ui.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.parentAps.ui.data.model.Weather

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather WHERE cityId = :cityId")
     fun getCityWeather(cityId: String): LiveData<List<Weather>>

    @Query("SELECT * FROM weather WHERE cityId = :cityId")
     fun getCityWeatherForDelete(cityId: String): List<Weather>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert(weather: Weather): Long

    @Delete
     fun delete(weather: Weather)
}