package com.example.lechendasapp.data.repository

import com.example.lechendasapp.data.source.local.ClimateDao
import javax.inject.Inject
import javax.inject.Singleton
import com.example.lechendasapp.data.model.Climate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Singleton
class DefaultClimateRepository @Inject constructor(
    private val localDataSource: ClimateDao,
) : ClimateRepository {
    override fun getClimateStream(): Flow<List<Climate>> {
        return localDataSource.observeAll().map { climate ->
            climate.toExternal()
        }
    }

    override fun getClimateStreamByMonitorLogId(monitorLogId: Long): Flow<List<Climate>> {
        return localDataSource.observeByMonitorLogId(monitorLogId).map { climate ->
            climate.toExternal()
        }
    }

    override fun getIndividualClimateStream(climateId: Long): Flow<Climate> {
        return localDataSource.observeById(climateId).map { it.toExternal() }
    }

    override suspend fun getClimate(): List<Climate> {
        return localDataSource.getAll().toExternal()
    }

    override suspend fun getClimateById(climateId: Long): Climate? {
        return localDataSource.getById(climateId)?.toExternal()
    }

    override suspend fun insertClimate(climate: Climate): Long {
        return localDataSource.insert(climate.toLocal())
    }

    override suspend fun updateClimate(climate: Climate) {
        localDataSource.update(climate.toLocal())
    }

    override suspend fun deleteClimate(climate: Climate) {
        localDataSource.delete(climate.toLocal())
    }

    override suspend fun deleteClimateById(climateId: Long) {
        localDataSource.deleteById(climateId)
    }

}