package com.example.lechendasapp.data.repository

import com.example.lechendasapp.data.model.MonitorLog
import com.example.lechendasapp.data.source.local.LocalMonitorLog

// External (App) to Local
fun MonitorLog.toLocal() = LocalMonitorLog(
    userId = userId,
    speciesId = speciesId,
    dateMillis = dateMillis,
    gpsCoordinates = gpsCoordinates,
    climateType = climateType,
    habitatType = habitatType,
    observations = observations,
    sightingMethod = sightingMethod
)

fun List<MonitorLog>.toLocal() = map { it.toLocal() }

// Local to External (App)
fun LocalMonitorLog.toExternal() = MonitorLog(
    id = id,
    userId = userId,
    speciesId = speciesId,
    dateMillis = dateMillis,
    gpsCoordinates = gpsCoordinates,
    climateType = climateType,
    habitatType = habitatType,
    observations = observations,
    sightingMethod = sightingMethod
)

@JvmName("localToExternalMonitorLogs")
fun List<LocalMonitorLog>.toExternal() = map { it.toExternal() }