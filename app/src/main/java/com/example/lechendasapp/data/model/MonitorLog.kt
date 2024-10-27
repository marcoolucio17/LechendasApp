package com.example.lechendasapp.data.model

data class MonitorLog (
    val id: Long,
    val userId: Long,
    val speciesId: Long,
    val dateMillis: Long,
    val gpsCoordinates: String,
    val climateType: String?,
    val habitatType: String?,
    val observations: String?,
    val sightingMethod: String,
)