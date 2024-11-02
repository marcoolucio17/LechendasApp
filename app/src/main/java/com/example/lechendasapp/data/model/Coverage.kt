package com.example.lechendasapp.data.model

data class Coverage(
    val id: Long,
    val monitorLogId: Long,
    val code: String,
    val tracking: String, //TODO: MAYBE bool
    val change: String, //TODO: MAYBE bool
    val coverage: String,
    val cropType: String,
    val disturbance: String,
    val observations: String? = null,
)
