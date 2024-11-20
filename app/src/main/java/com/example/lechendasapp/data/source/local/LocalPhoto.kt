package com.example.lechendasapp.data.source.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//TODO: Remove if network is used to store images
//TODO: implement overriding of equals

@Entity(tableName = "photos")
data class LocalPhoto (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "forms_id") val formsId: Long,
    @ColumnInfo(name = "monitor_log_id") val monitorLogId: Long,
    @ColumnInfo(name = "file_path") val filePath: String,
    val image: ByteArray?,
    val description: String? = null
)
