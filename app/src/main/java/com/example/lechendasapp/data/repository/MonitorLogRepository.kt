package com.example.lechendasapp.data.repository

import com.example.lechendasapp.data.model.MonitorLog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface MonitorLogRepository {
    fun getMonitorLogsStream(): Flow<List<MonitorLog>>

    fun getMonitorLogByIdStream(monitorLogId: Long): Flow<MonitorLog>

    suspend fun getAllMonitorLogs(): List<MonitorLog>

    suspend fun getMonitorLogById(monitorLogId: Long): MonitorLog?

    suspend fun addMonitorLog(monitorLog: MonitorLog)

    suspend fun deleteMonitorLog(monitorLog: MonitorLog)
}