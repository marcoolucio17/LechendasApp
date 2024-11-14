package com.example.lechendasapp.fakes

import com.example.lechendasapp.data.model.Vegetation
import com.example.lechendasapp.data.repository.VegetationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class FakeVegetationRepository : VegetationRepository {

    private val vegetationList = mutableListOf<Vegetation>()
    var lastInsertedVegetation: Vegetation? = null

    override fun getVegetationStream(): Flow<List<Vegetation>> {
        return flowOf(vegetationList)
    }

    override fun getVegetationStreamByMonitorLogId(monitorLogId: Long): Flow<List<Vegetation>> {
        TODO("Not yet implemented")
    }

    override fun getIndividualVegetationStream(vegetationId: Long): Flow<Vegetation> {
        val vegetation = vegetationList.find { it.id == vegetationId }
        return flow {
            if (vegetation != null) emit(vegetation)
        }
    }

    override suspend fun getVegetation(): List<Vegetation> {
        return vegetationList
    }

    override suspend fun getVegetationById(vegetationId: Long): Vegetation? {
        return vegetationList.find { it.id == vegetationId }
    }

    override suspend fun insertVegetation(vegetation: Vegetation) {
        lastInsertedVegetation = vegetation
        vegetationList.add(vegetation)
    }

    override suspend fun updateVegetation(vegetation: Vegetation) {
        val index = vegetationList.indexOfFirst { it.id == vegetation.id }
        if (index != -1) {
            vegetationList[index] = vegetation
        }
    }

    override suspend fun deleteVegetation(vegetation: Vegetation) {
        vegetationList.remove(vegetation)
    }

    override suspend fun deleteVegetationById(vegetationId: Long) {
        TODO("Not yet implemented")
    }
}