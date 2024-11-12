package com.example.lechendasapp.data.model

data class MonitorLog (
    val id: Long,
    val userId: Long,

    val dateMillis: Long,
    val location: String,
    val gpsCoordinates: String,

    val climateType: String,
    val seasons: String,
    val logType: String,

    val zone: String,
)