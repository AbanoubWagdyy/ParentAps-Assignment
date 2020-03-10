package com.parentAps.ui.homepage.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.parentAps.ui.homepage.data.model.Weather

@Dao
interface HomepageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weather: Weather)
}