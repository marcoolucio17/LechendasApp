package com.example.lechendasapp.data.repository

import com.example.lechendasapp.data.model.Coverage
import com.example.lechendasapp.data.source.local.LocalCoverage


fun Coverage.toLocal() = LocalCoverage(
    id = id,
    monitorLogId = monitorLogId,
    code = code,
    tracking = tracking,
    change = change,
    coverage = coverage,
    cropType = cropType,
    disturbance = disturbance,
    observations = observations,
)

fun List<Coverage>.toLocal() = map { it.toLocal() }

fun LocalCoverage.toExternal() = Coverage(
    id = id,
    monitorLogId = monitorLogId,
    code = code,
    tracking = tracking,
    change = change,
    coverage = coverage,
    cropType = cropType,
    disturbance = disturbance,
    observations = observations,
)

@JvmName("localToExternalCoverage")
fun List<LocalCoverage>.toExternal() = map { it.toExternal() }
