package com.example.lechendasapp.data.source.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "monitor_logs")
data class LocalMonitorLog(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo(name = "user_id") val userId: Long,
    @ColumnInfo(name = "species_id") val speciesId: Long,
    @ColumnInfo(name = "date_millis") val dateMillis: Long,
    @ColumnInfo(name = "gps_coordinates") val gpsCoordinates: String,
    @ColumnInfo(name = "climate_type") val climateType: String? = null,
    @ColumnInfo(name = "habitat_type") val habitatType: String? = null,
    val observations: String? = null,
    @ColumnInfo(name = "sighting_method") val sightingMethod: String,
)