package com.example.lechendasapp.data.model

data class Trap(
    val id: Long,
    val monitorLogId: Long,
    val code: String,
    val cameraName: String,
    val cameraPlate: String,
    val guayaPlate: String,
    val roadWidth: Int,
    val installationDate : String,
    val lensHeight: Int,
    val checkList: String,
    val observations: String? = null,
)
