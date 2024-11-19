package com.example.lechendasapp.data.model

data class Photo (
    val id: Long,
    val formsId: Long,
    val monitorLogId: Long,
    val filePath: String,
    val image: ByteArray?,
    val description: String?
)