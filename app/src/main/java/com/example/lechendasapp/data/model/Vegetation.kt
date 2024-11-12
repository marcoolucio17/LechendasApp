package com.example.lechendasapp.data.model

data class Vegetation(
    val id: Long,
    val monitorLogId: Long,
    val code: String,
    val quadrant: String,
    val subQuadrant: String,
    val growthHabit: String,
    val commonName: String,
    val scientificName: String? = null,
    val plate: String,
    val circumference: Int,
    val distance: Int,
    val height: Int,
    val observations: String? = null,
)
