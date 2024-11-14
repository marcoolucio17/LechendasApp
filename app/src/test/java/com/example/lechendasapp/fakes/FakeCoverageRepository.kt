package com.example.lechendasapp.fakes

import com.example.lechendasapp.data.model.Coverage
import com.example.lechendasapp.data.repository.CoverageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf

class FakeCoverageRepository : CoverageRepository {

    private val coverageData = mutableListOf<Coverage>()
    private val coverageFlow = MutableStateFlow<List<Coverage>>(coverageData)

    override fun getConverageStream(): Flow<List<Coverage>> {
        return coverageFlow.asStateFlow()
    }

    override fun getConverageStreamByMonitorLogId(monitorLogId: Long): Flow<List<Coverage>> {
        val filteredCoverages = coverageData.filter { it.monitorLogId == monitorLogId }
        return flowOf(filteredCoverages)
    }

    override fun getIndividualConverageStream(converageId: Long): Flow<Coverage> {
        val coverage = coverageData.find { it.id == converageId }
        return if (coverage != null) flowOf(coverage) else flowOf()
    }

    override suspend fun getConverage(): List<Coverage> {
        return coverageData.toList()
    }

    override suspend fun getConverageById(converageId: Long): Coverage? {
        return coverageData.find { it.id == converageId }
    }

    override suspend fun insertConverage(converage: Coverage) {
        coverageData.add(converage)
        coverageFlow.value = coverageData.toList()
    }

    override suspend fun updateConverage(converage: Coverage) {
        coverageData.replaceAll { if (it.id == converage.id) converage else it }
        coverageFlow.value = coverageData.toList()
    }

    override suspend fun deleteConverage(converage: Coverage) {
        coverageData.remove(converage)
        coverageFlow.value = coverageData.toList()
    }

    override suspend fun deleteConverageById(converageId: Long) {
        coverageData.removeAll { it.id == converageId }
        coverageFlow.value = coverageData.toList()
    }
}
