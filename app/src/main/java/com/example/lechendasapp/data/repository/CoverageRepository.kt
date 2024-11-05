package com.example.lechendasapp.data.repository

import kotlinx.coroutines.flow.Flow
import com.example.lechendasapp.data.model.Coverage

interface CoverageRepository {
    fun getConverageStream(): Flow<List<Coverage>>

    fun getIndividualConverageStream(converageId: Long): Flow<Coverage>

    suspend fun getConverage(): List<Coverage>

    suspend fun getConverageById(converageId: Long): Coverage?

    suspend fun insertConverage(converage: Coverage)

    suspend fun updateConverage(converage: Coverage)

    suspend fun deleteConverage(converage: Coverage)
}