package com.example.lechendasapp.data.repository

import com.example.lechendasapp.data.model.Coverage
import com.example.lechendasapp.data.source.local.CoverageDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultCoverageRepository @Inject constructor(
    private val localDataSource: CoverageDao,
) : CoverageRepository {
    override fun getConverageStream(): Flow<List<Coverage>> {
        return localDataSource.observeAll().map { animals ->
            animals.toExternal()
        }
    }

    override fun getConverageStreamByMonitorLogId(monitorLogId: Long): Flow<List<Coverage>> {
        return localDataSource.observeByMonitorLogId(monitorLogId).map { coverage ->
            coverage.toExternal()
        }
    }

    override fun getIndividualConverageStream(converageId: Long): Flow<Coverage> {
        return localDataSource.observeById(converageId).map { it.toExternal() }
    }

    override suspend fun getConverage(): List<Coverage> {
        return localDataSource.getAll().toExternal()
    }

    override suspend fun getConverageById(converageId: Long): Coverage? {
        return localDataSource.getById(converageId)?.toExternal()
    }

    override suspend fun insertConverage(converage: Coverage): Long {
        return localDataSource.insert(converage.toLocal())
    }

    override suspend fun updateConverage(converage: Coverage) {
        localDataSource.update(converage.toLocal())
    }

    override suspend fun deleteConverage(converage: Coverage) {
        localDataSource.delete(converage.toLocal())
    }

    override suspend fun deleteConverageById(converageId: Long) {
        localDataSource.deleteById(converageId)
    }

    override suspend fun countConverage(): Int {
        return localDataSource.count()
    }

    override suspend fun deleteConverageByMonitorLogId(monitorLogId: Long) {
        localDataSource.deleteByMonitorLogId(monitorLogId)
    }
}