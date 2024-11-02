package com.example.lechendasapp.data.repository

import com.example.lechendasapp.data.model.Climate
import kotlinx.coroutines.flow.Flow


interface ClimateRepository {
    fun getClimateStream(): Flow<List<Climate>>

    fun getIndividualClimateStream(climateId: Long): Flow<Climate>

    suspend fun getClimate(): List<Climate>

    suspend fun getClimateById(climateId: Long): Climate?

    suspend fun insertClimate(climate: Climate)

    suspend fun updateClimate(climate: Climate)

    suspend fun deleteClimate(climate: Climate)
}