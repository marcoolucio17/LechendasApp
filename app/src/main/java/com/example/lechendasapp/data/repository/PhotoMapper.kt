package com.example.lechendasapp.data.repository

import com.example.lechendasapp.data.model.Photo
import com.example.lechendasapp.data.source.local.LocalPhoto

fun Photo.toLocal() = LocalPhoto(
    id = id,
    formsId = formsId,
    monitorLogId = monitorLogId,
    filePath = filePath,
    image = image,
    description = description
)

fun List<Photo>.toLocal() = map { it.toLocal() }

fun LocalPhoto.toExternal() = Photo(
    id = id,
    formsId = formsId,
    monitorLogId = monitorLogId,
    filePath = filePath,
    image = image,
    description = description
)

@JvmName("localToExternalPhoto")
fun List<LocalPhoto>.toExternal() = map { it.toExternal() }