package com.parentAps.ui.homepage.data.model

import androidx.room.*
import com.google.gson.annotations.SerializedName

@Entity(tableName = "weather")
data class Weather(
    @PrimaryKey
    @field:SerializedName("id")
    val id: Int,
    val base: String,
    val cod: Int,
    val dt: Int,
    val name: String,
    val visibility: Int,
    @Embedded
    val wind: Wind,
    @Embedded
    val clouds: Clouds,
    @Embedded
    val coord: Coord,
    @Embedded
    val main: Main,
    @Embedded
    val sys: Sys
)