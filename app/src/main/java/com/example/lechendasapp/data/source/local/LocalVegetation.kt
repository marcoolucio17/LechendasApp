package com.example.lechendasapp.data.source.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vegetation")
data class LocalVegetation(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo(name = "monitor_log_id") val monitorLogId: Long,
    val code: String,
    val quadrant: String,
    @ColumnInfo(name = "sub_quadrant") val subQuadrant: String,
    @ColumnInfo(name = "growth_habit") val growthHabit: String,
    @ColumnInfo(name = "common_name") val commonName: String,
    @ColumnInfo(name = "scientific_name") val scientificName: String? = null,
    val plate: String,
    val circumference: Int,
    val distance: Int,
    val height: Int,
    val observations: String? = null,
)