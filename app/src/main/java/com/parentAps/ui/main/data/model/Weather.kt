package com.parentAps.ui.main.data.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class Weather(
    @PrimaryKey
    @NonNull
    var cityId: String,
    var cityName: String?,
    var country: String?,
    var latitude: Double?,
    var longitude: Double?,
    var weatherInfoList: String?

) {
    constructor() : this("", "", "", 0.0, 0.0, "")
}