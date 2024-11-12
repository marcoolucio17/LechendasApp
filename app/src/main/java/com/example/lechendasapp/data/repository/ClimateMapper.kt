package com.example.lechendasapp.data.repository

import com.example.lechendasapp.data.model.Climate
import com.example.lechendasapp.data.source.local.LocalClimate

fun Climate.toLocal() = LocalClimate(
    id = id,
    monitorLogId = monitorLogId,
    rainfall = rainfall,
    maxTemp = maxTemp,
    minTemp = minTemp,
    maxHumidity = maxHumidity,
    minHumidity = minHumidity,
    ravineLevel = ravineLevel,
    observations = observations,
)

fun List<Climate>.toLocal() = map { it.toLocal() }

fun LocalClimate.toExternal() = Climate(
    id = id,
    monitorLogId = monitorLogId,
    rainfall = rainfall,
    maxTemp = maxTemp,
    minTemp = minTemp,
    maxHumidity = maxHumidity,
    minHumidity = minHumidity,
    ravineLevel = ravineLevel,
    observations = observations,
)

@JvmName("localToExternalClimate")
fun List<LocalClimate>.toExternal() = map { it.toExternal() }