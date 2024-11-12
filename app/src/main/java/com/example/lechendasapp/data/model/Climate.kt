package com.example.lechendasapp.data.model

data class Climate(
    val id: Long,
    val monitorLogId: Long,
    val rainfall: Int,
    val maxTemp: Int,
    val minTemp: Int,
    val maxHumidity: Int,
    val minHumidity: Int,
    val ravineLevel: Int,
    val observations: String? = null,
)
