package com.example.lechendasapp.data.repository

import com.example.lechendasapp.data.source.local.TrapDao
import javax.inject.Inject
import javax.inject.Singleton
import com.example.lechendasapp.data.model.Trap
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Singleton
class DefaultTrapRepository @Inject constructor(
    private val localDataSource: TrapDao,
) : TrapRepository {
    override fun getTrapStream(): Flow<List<Trap>> {
        return localDataSource.observeAll().map { traps ->
            traps.toExternal()
        }
    }

    override fun getTrapStreamByMonitorLogId(monitorLogId: Long): Flow<List<Trap>> {
        return localDataSource.observeByMonitorLogId(monitorLogId).map { traps ->
            traps.toExternal()
        }
    }

    override fun getIndividualTrapStream(trapId: Long): Flow<Trap> {
        return localDataSource.observeById(trapId).map { it.toExternal() }
    }


    override suspend fun getTrap(): List<Trap> {
        return localDataSource.getAll().toExternal()
    }

    override suspend fun getTrapById(trapId: Long): Trap? {
        return localDataSource.getById(trapId)?.toExternal()
    }


    override suspend fun insertTrap(trap: Trap): Long {
        return localDataSource.insert(trap.toLocal())
    }

    override suspend fun updateTrap(trap: Trap) {
        localDataSource.update(trap.toLocal())
    }

    override suspend fun deleteTrap(trap: Trap) {
        localDataSource.delete(trap.toLocal())
    }

    override suspend fun deleteTrapById(trapId: Long) {
        localDataSource.deleteById(trapId)
    }

    override suspend fun countTrap(): Int {
        return localDataSource.count()
    }

    override suspend fun deleteTrapByMonitorLogId(monitorLogId: Long) {
        localDataSource.deleteByMonitorLogId(monitorLogId)
    }

}