package com.example.lechendasapp.data.repository

import com.example.lechendasapp.data.model.Trap
import kotlinx.coroutines.flow.Flow

interface TrapRepository {
    fun getTrapStream(): Flow<List<Trap>>
    fun getTrapStreamByMonitorLogId(monitorLogId: Long): Flow<List<Trap>>
    fun getIndividualTrapStream(trapId: Long): Flow<Trap>

    suspend fun getTrap(): List<Trap>
    suspend fun getTrapById(trapId: Long): Trap?

    suspend fun insertTrap(trap: Trap)
    suspend fun updateTrap(trap: Trap)
    suspend fun deleteTrap(trap: Trap)
    suspend fun deleteTrapById(trapId: Long)


}