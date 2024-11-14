package com.example.lechendasapp.fakes

import com.example.lechendasapp.data.model.Climate
import com.example.lechendasapp.data.repository.ClimateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeClimateRepository : ClimateRepository {

    private val climateList = mutableListOf<Climate>()
    private val climateFlow = MutableStateFlow<List<Climate>>(emptyList())

    override fun getClimateStream(): Flow<List<Climate>> {
        return climateFlow
    }

    override fun getClimateStreamByMonitorLogId(monitorLogId: Long): Flow<List<Climate>> {
        return climateFlow.map { climates -> climates.filter { it.monitorLogId == monitorLogId } }
    }

    override fun getIndividualClimateStream(climateId: Long): Flow<Climate> {
        return climateFlow.map { climates ->
            climates.first { it.id == climateId }
        }
    }

    override suspend fun getClimate(): List<Climate> {
        return climateList.toList()
    }

    override suspend fun getClimateById(climateId: Long): Climate? {
        return climateList.find { it.id == climateId }
    }

    override suspend fun insertClimate(climate: Climate) {
        climateList.add(climate)
        emitChanges()
    }

    override suspend fun updateClimate(climate: Climate) {
        val index = climateList.indexOfFirst { it.id == climate.id }
        if (index != -1) {
            climateList[index] = climate
            emitChanges()
        }
    }

    override suspend fun deleteClimate(climate: Climate) {
        climateList.remove(climate)
        emitChanges()
    }

    override suspend fun deleteClimateById(climateId: Long) {
        climateList.removeAll { it.id == climateId }
        emitChanges()
    }

    private fun emitChanges() {
        climateFlow.value = climateList.toList()
    }
}