package com.example.lechendasapp.data.repository

import com.example.lechendasapp.data.model.Vegetation
import com.example.lechendasapp.data.source.local.LocalVegetation

fun Vegetation.toLocal() = LocalVegetation(
    id = id,
    monitorLogId = monitorLogId,
    code = code,
    quadrant = quadrant,
    subQuadrant = subQuadrant,
    growthHabit = growthHabit,
    commonName = commonName,
    scientificName = scientificName,
    plate = plate,
    circumference = circumference,
    distance = distance,
    height = height,
    observations = observations,
)

fun List<Vegetation>.toLocal() = map { it.toLocal() }

fun LocalVegetation.toExternal() = Vegetation(
    id = id,
    monitorLogId = monitorLogId,
    code = code,
    quadrant = quadrant,
    subQuadrant = subQuadrant,
    growthHabit = growthHabit,
    commonName = commonName,
    scientificName = scientificName,
    plate = plate,
    circumference = circumference,
    distance = distance,
    height = height,
    observations = observations,
)

@JvmName("localToExternalVegetation")
fun List<LocalVegetation>.toExternal() = map { it.toExternal() }