package com.example.lechendasapp.data.source.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trap")
data class LocalTrap (
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo(name = "monitor_log_id") val monitorLogId: Long,
    val code: String,
    @ColumnInfo(name = "camera_name") val cameraName: String,
    @ColumnInfo(name = "camera_plate") val cameraPlate: String,
    @ColumnInfo(name = "guaya_plate") val guayaPlate: String,
    val roadWidth: Int,
    @ColumnInfo(name = "installation_date") val installationDate : String,
    @ColumnInfo(name = "lens_height")  val lensHeight: Int,
    val checkList: String,
    val observations: String? = null,
)