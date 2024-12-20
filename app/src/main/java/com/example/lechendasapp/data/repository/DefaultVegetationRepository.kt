package com.example.lechendasapp.data.repository

import com.example.lechendasapp.data.model.Vegetation
import com.example.lechendasapp.data.source.local.VegetationDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultVegetationRepository @Inject constructor(
    private val localDataSource: VegetationDao,
) : VegetationRepository {
    override fun getVegetationStream(): Flow<List<Vegetation>> {
        return localDataSource.observeAll().map { vegetation ->
            vegetation.toExternal()
        }
    }

    override fun getVegetationStreamByMonitorLogId(monitorLogId: Long): Flow<List<Vegetation>> {
        return localDataSource.observeByMonitorLogId(monitorLogId).map { vegetation ->
            vegetation.toExternal()
        }
    }

    override fun getIndividualVegetationStream(vegetationId: Long): Flow<Vegetation> {
        return localDataSource.observeById(vegetationId).map { it.toExternal() }
    }

    override suspend fun getVegetation(): List<Vegetation> {
        return localDataSource.getAll().toExternal()
    }

    override suspend fun getVegetationById(vegetationId: Long): Vegetation? {
        return localDataSource.getById(vegetationId)?.toExternal()
    }

    override suspend fun insertVegetation(vegetation: Vegetation): Long {
        return localDataSource.insert(vegetation.toLocal())
    }

    override suspend fun updateVegetation(vegetation: Vegetation) {
        localDataSource.update(vegetation.toLocal())
    }

    override suspend fun deleteVegetation(vegetation: Vegetation) {
        localDataSource.delete(vegetation.toLocal())
    }

    override suspend fun deleteVegetationById(vegetationId: Long) {
        localDataSource.deleteById(vegetationId)
    }

    override suspend fun countVegetation(): Int {
        return localDataSource.count()
    }

    override suspend fun deleteVegetationByMonitorLogId(monitorLogId: Long) {
        localDataSource.deleteByMonitorLogId(monitorLogId)
    }


}