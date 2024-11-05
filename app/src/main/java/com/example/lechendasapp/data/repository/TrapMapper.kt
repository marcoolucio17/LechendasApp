package com.example.lechendasapp.data.repository

import com.example.lechendasapp.data.model.Trap
import com.example.lechendasapp.data.source.local.LocalTrap

fun Trap.toLocal() = LocalTrap(
    id = id,
    monitorLogId = monitorLogId,
    code = code,
    cameraName = cameraName,
    cameraPlate = cameraPlate,
    guayaPlate = guayaPlate,
    roadWidth = roadWidth,
    installationDate = installationDate,
    lensHeight = lensHeight,
    checkList = checkList,
    objectiveDistance = objectiveDistance,
    observations = observations,
)

fun List<Trap>.toLocal() = map { it.toLocal() }

fun LocalTrap.toExternal() = Trap(
    id = id,
    monitorLogId = monitorLogId,
    code = code,
    cameraName = cameraName,
    cameraPlate = cameraPlate,
    guayaPlate = guayaPlate,
    roadWidth = roadWidth,
    installationDate = installationDate,
    lensHeight = lensHeight,
    checkList = checkList,
    objectiveDistance = objectiveDistance,
    observations = observations,
)

@JvmName("localToExternalTrap")
fun List<LocalTrap>.toExternal() = map { it.toExternal() }

