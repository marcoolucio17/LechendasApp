package com.example.lechendasapp.data.repository

import com.example.lechendasapp.data.model.Animal
import com.example.lechendasapp.data.source.local.LocalAnimal


fun Animal.toLocal() = LocalAnimal (
    id = id,
    monitorLogId = monitorLogId,
    animalType = animalType,
    commonName = commonName,
    scientificName = scientificName,
    quantity = quantity,
    observationType = observationType,
    transectName = transectName,
    observationHeight = observationHeight,
    observations = observations,
)

fun List<Animal>.toLocal() = map { it.toLocal() }

fun LocalAnimal.toExternal() = Animal (
    id = id,
    monitorLogId = monitorLogId,
    animalType = animalType,
    commonName = commonName,
    scientificName = scientificName,
    quantity = quantity,
    observationType = observationType,
    transectName = transectName,
    observationHeight = observationHeight,
    observations = observations,
)


//PUEDE TENER ERROR DE COMPILACIÓN, EN TAL CASO AGREGAR @JvmName("localToExternal")
// P.D. pueden cambiar lo que esta entre ""
@JvmName("localToExternalAnimal")
fun List<LocalAnimal>.toExternal() = map { it.toExternal() }
