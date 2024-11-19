package com.example.lechendasapp.data.repository

import com.example.lechendasapp.data.model.MonitorLog
import com.example.lechendasapp.data.source.local.MonitorLogDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultMonitorLogRepository @Inject constructor(
    private val localDataSource: MonitorLogDao,
) : MonitorLogRepository {
    override fun getMonitorLogsStream(): Flow<List<MonitorLog>> {
        return localDataSource.observeAll().map { it.toExternal() }
    }

    override fun getMonitorLogByIdStream(monitorLogId: Long): Flow<MonitorLog> {
        return localDataSource.observeById(monitorLogId).map { it.toExternal() }
    }

    override suspend fun getAllMonitorLogs(): List<MonitorLog> {
        return localDataSource.getAll().toExternal()
    }

    override suspend fun getMonitorLogById(monitorLogId: Long): MonitorLog? {
        return localDataSource.getById(monitorLogId)?.toExternal()
    }

    override suspend fun addMonitorLog(monitorLog: MonitorLog): Long {
        return localDataSource.insert(monitorLog.toLocal()).toLong()
    }

    override suspend fun deleteMonitorLog(monitorLog: MonitorLog) {
        localDataSource.delete(monitorLog.toLocal())
    }

    override suspend fun deleteMonitorLogById(monitorLogId: Long) {
        localDataSource.deleteById(monitorLogId)
    }

    override suspend fun countMonitorLog(): Int {
        return localDataSource.count()
    }

}