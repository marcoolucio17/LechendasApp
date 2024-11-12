package com.example.lechendasapp.data.source.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "animals")
data class LocalAnimal (
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo(name = "monitor_log_id") val monitorLogId: Long,
    @ColumnInfo(name = "animal_type") val animalType: String,
    @ColumnInfo(name = "common_name") val commonName: String,
    @ColumnInfo(name = "scientific_name") val scientificName: String? = null,
    @ColumnInfo(name = "quantity") val quantity: Int,
    @ColumnInfo(name = "observation_type") val observationType: String,
    @ColumnInfo(name = "transect_name") val transectName: String? = null,
    @ColumnInfo(name = "observation_height") val observationHeight: String? = null,
    val observations: String? = null,
)