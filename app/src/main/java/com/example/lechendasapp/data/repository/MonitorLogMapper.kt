package com.example.lechendasapp.data.repository

import com.example.lechendasapp.data.model.MonitorLog
import com.example.lechendasapp.data.source.local.LocalMonitorLog

// External (App) to Local
fun MonitorLog.toLocal() = LocalMonitorLog(
    id = id,
    userId = userId,

    dateMillis = dateMillis,
    location = location,
    gpsCoordinates = gpsCoordinates,

    climateType = climateType,
    seasons = seasons,
    logType = logType,

    zone = zone
)

fun List<MonitorLog>.toLocal() = map { it.toLocal() }

// Local to External (App)
fun LocalMonitorLog.toExternal() = MonitorLog(
    id = id,
    userId = userId,

    dateMillis = dateMillis,
    location = location,
    gpsCoordinates = gpsCoordinates,

    climateType = climateType,
    seasons = seasons,
    logType = logType,

    zone = zone
)

@JvmName("localToExternalMonitorLogs")
fun List<LocalMonitorLog>.toExternal() = map { it.toExternal() }