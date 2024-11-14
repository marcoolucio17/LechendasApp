package com.example.lechendasapp.fakes

import com.example.lechendasapp.data.model.Trap
import com.example.lechendasapp.data.repository.TrapRepository
import kotlinx.coroutines.flow.Flow

class FakeTrapRepository : TrapRepository {
    val traps = mutableListOf<Trap>()
    override fun getTrapStream(): Flow<List<Trap>> {
        TODO("Not yet implemented")
    }

    override fun getTrapStreamByMonitorLogId(monitorLogId: Long): Flow<List<Trap>> {
        TODO("Not yet implemented")
    }

    override fun getIndividualTrapStream(trapId: Long): Flow<Trap> {
        TODO("Not yet implemented")
    }

    override suspend fun getTrap(): List<Trap> {
        TODO("Not yet implemented")
    }

    override suspend fun getTrapById(id: Long): Trap? {
        return traps.find { it.id == id }
    }

    override suspend fun insertTrap(trap: Trap) {
        traps.add(trap)
    }

    override suspend fun updateTrap(trap: Trap) {
        traps.replaceAll { if (it.id == trap.id) trap else it }
    }

    override suspend fun deleteTrap(trap: Trap) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTrapById(trapId: Long) {
        TODO("Not yet implemented")
    }
}