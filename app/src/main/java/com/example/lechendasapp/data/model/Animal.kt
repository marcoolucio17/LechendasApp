package com.example.lechendasapp.data.model

data class Animal (
    val id: Long,
    val monitorLogId: Long,
    val animalType: String,
    val commonName: String,
    val scientificName: String? = null,
    val quantity: Int,
    val observationType: String,
    val transectName: String? = null,
    val observationHeight: String? = null,
    val observations: String? = null,
)