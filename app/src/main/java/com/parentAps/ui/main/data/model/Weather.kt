package com.parentAps.ui.main.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity
class Weather {

    @PrimaryKey
    var city_id: String? = ""
    var city_name: String? = ""
    var country: String? = ""
    var latitude: Double? = 0.0
    var longitude: Double? = 0.0

    @TypeConverters
    var weatherInfo: List<WeatherInfo>? = null
}