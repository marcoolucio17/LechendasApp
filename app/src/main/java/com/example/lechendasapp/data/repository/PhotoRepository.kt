package com.example.lechendasapp.data.repository

import com.example.lechendasapp.data.model.Photo
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {
    fun getPhotoStream(): Flow<List<Photo>>
    fun getPhotoStreamByMonitorLogFormsId(monitorLogId: Long, formsId: Long): Flow<List<Photo>>
    fun getPhotoStreamByNull(): Flow<List<Photo>>
    fun getIndividualPhotoStream(photoId: Long): Flow<Photo>

    suspend fun getPhoto(): List<Photo>
    suspend fun getPhotoById(photoId: Long): Photo?
    suspend fun insertPhoto(photo: Photo)
    suspend fun updatePhoto(photo: Photo)
    suspend fun deletePhoto(photo: Photo)
    suspend fun deletePhotoByNull()
    suspend fun countPhoto(): Int
    suspend fun deletePhotoByMonitorFormsId(monitorLogId: Long)



}