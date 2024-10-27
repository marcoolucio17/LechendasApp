package com.example.lechendasapp.data.source.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "species")
data class LocalSpecies(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo(name = "monitor_log_id") val monitorLogId: Long,
    @ColumnInfo(name = "scientific_name") val scientificName: String? = null,
    @ColumnInfo(name = "common_name") val commonName: String,
    val sex: String? = null,
    @ColumnInfo(name = "speciesClass") val speciesClass: String? = null,
)