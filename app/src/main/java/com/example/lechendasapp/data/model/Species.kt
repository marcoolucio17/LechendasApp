package com.example.lechendasapp.data.model

data class Species (
    val id: Long,
    val monitorLogId: Long,
    val scientificName: String?,
    val commonName: String,
    val sex: String?,
    val speciesClass: String?,
)
