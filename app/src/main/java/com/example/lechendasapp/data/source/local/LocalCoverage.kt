package com.example.lechendasapp.data.source.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coverage")
data class LocalCoverage(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo(name = "monitor_log_id") val monitorLogId: Long,
    val code: String,
    val tracking: String, //TODO: MAYBE bool
    val change: String, //TODO: MAYBE bool
    val coverage: String,
    @ColumnInfo(name = "crop_type") val cropType: String,
    val disturbance: String,
    val observations: String? = null,
)