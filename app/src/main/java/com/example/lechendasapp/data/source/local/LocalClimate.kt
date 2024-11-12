package com.example.lechendasapp.data.source.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "climate")
data class LocalClimate (
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo(name = "monitor_log_id") val monitorLogId: Long,
    val rainfall: Int,
    val maxTemp: Int,
    val minTemp: Int,
    val maxHumidity: Int,
    val minHumidity: Int,
    val ravineLevel: Int,
    val observations: String? = null,
)