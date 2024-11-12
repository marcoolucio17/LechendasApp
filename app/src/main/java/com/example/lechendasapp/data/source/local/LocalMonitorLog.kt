package com.example.lechendasapp.data.source.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "monitor_logs")
data class LocalMonitorLog(
    //Keys
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo(name = "user_id") val userId: Long,

    @ColumnInfo(name = "date_time") val dateMillis: Long,
    val location: String,
    @ColumnInfo(name = "gps_coordinates") val gpsCoordinates: String,

    @ColumnInfo(name = "climate_type") val climateType: String,
    @ColumnInfo(name = "seasons") val seasons: String,
    @ColumnInfo(name = "log_type") val logType: String,

    val zone: String,
)