package com.example.lechendasapp.fakes

import com.example.lechendasapp.data.model.Climate
import com.example.lechendasapp.data.repository.ClimateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class FakeClimateRepository : ClimateRepository {

    private val climates = mutableListOf<Climate>()
    var lastInsertedClimate: Climate? = null

    override fun getClimateStream(): Flow<List<Climate>> {
        return flowOf(climates)
    }

    override fun getIndividualClimateStream(climateId: Long): Flow<Climate> {
        val climate = climates.find { it.id == climateId }
        return flow {
            if (climate != null) emit(climate)
        }
    }

    override suspend fun getClimate(): List<Climate> {
        return climates
    }

    override suspend fun getClimateById(climateId: Long): Climate? {
        return climates.find { it.id == climateId }
    }

    override suspend fun insertClimate(climate: Climate) {
        lastInsertedClimate = climate
        climates.add(climate)
    }

    override suspend fun updateClimate(climate: Climate) {
        val index = climates.indexOfFirst { it.id == climate.id }
        if (index != -1) {
            climates[index] = climate
        }
    }

    override suspend fun deleteClimate(climate: Climate) {
        climates.remove(climate)
    }
}
