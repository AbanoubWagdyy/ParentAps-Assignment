package com.parentAps.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.parentAps.ui.data.WeatherDao
import com.parentAps.ui.data.model.Weather

@Database(
    entities = [Weather::class],
    version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "weather-db")
                .allowMainThreadQueries()
                .build()
        }
    }
}