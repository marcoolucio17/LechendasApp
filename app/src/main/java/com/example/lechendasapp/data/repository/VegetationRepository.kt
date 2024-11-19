package com.example.lechendasapp.data.repository

import com.example.lechendasapp.data.model.Vegetation
import kotlinx.coroutines.flow.Flow

interface VegetationRepository {
    fun getVegetationStream(): Flow<List<Vegetation>>
    fun getVegetationStreamByMonitorLogId(monitorLogId: Long): Flow<List<Vegetation>>
    fun getIndividualVegetationStream(vegetationId: Long): Flow<Vegetation>
    suspend fun getVegetation(): List<Vegetation>
    suspend fun getVegetationById(vegetationId: Long): Vegetation?
    suspend fun insertVegetation(vegetation: Vegetation) : Long
    suspend fun updateVegetation(vegetation: Vegetation)
    suspend fun deleteVegetation(vegetation: Vegetation)
    suspend fun deleteVegetationById(vegetationId: Long)

}