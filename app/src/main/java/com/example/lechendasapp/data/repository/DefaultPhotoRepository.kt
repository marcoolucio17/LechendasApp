package com.example.lechendasapp.data.repository

import com.example.lechendasapp.data.source.local.PhotoDao
import javax.inject.Inject
import javax.inject.Singleton
import com.example.lechendasapp.data.model.Photo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Singleton
class DefaultPhotoRepository @Inject constructor(
    private val localDataSource: PhotoDao,
) : PhotoRepository {
    override fun getPhotoStream(): Flow<List<Photo>> {
        return localDataSource.observeAll().map { photos ->
            photos.toExternal()
        }
    }

    override fun getPhotoStreamByMonitorLogFormsId(monitorLogId: Long, formsId: Long): Flow<List<Photo>> {
        return localDataSource.observeByMonitorFormsId(monitorLogId, formsId).map { photos ->
            photos.toExternal()
        }
    }

    override fun getPhotoStreamByNull(): Flow<List<Photo>> {
        return localDataSource.observeByNull().map { photos ->
            photos.toExternal()
        }
    }

    override fun getIndividualPhotoStream(photoId: Long): Flow<Photo> {
        return localDataSource.observeById(photoId).map { it.toExternal() }
    }


    override suspend fun getPhoto(): List<Photo> {
        return localDataSource.getAll().toExternal()
    }

    override suspend fun getPhotoById(photoId: Long): Photo? {
        return localDataSource.getById(photoId)?.toExternal()
    }

    override suspend fun insertPhoto(photo: Photo) {
        localDataSource.insert(photo.toLocal())
    }

    override suspend fun updatePhoto(photo: Photo) {
        localDataSource.update(photo.toLocal())
    }

    override suspend fun deletePhoto(photo: Photo) {
        localDataSource.delete(photo.toLocal())
    }

    override suspend fun deletePhotoByNull() {
        localDataSource.deleteByNull()
    }

    override suspend fun countPhoto(): Int {
        return localDataSource.count()
    }

    override suspend fun deletePhotoByMonitorFormsId(monitorLogId: Long) {
        localDataSource.deleteByMonitorFormsId(monitorLogId)
    }


}