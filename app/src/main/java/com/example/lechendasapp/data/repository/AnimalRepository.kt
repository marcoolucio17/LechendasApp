package com.example.lechendasapp.data.repository

import com.example.lechendasapp.data.model.Animal
import kotlinx.coroutines.flow.Flow

interface AnimalRepository {
    fun getAnimalsStream(): Flow<List<Animal>>

    fun getAnimalsStreamByMonitorLogId(monitorLogId: Long): Flow<List<Animal>>

    fun getIndividualAnimalStream(animalId: Long): Flow<Animal>

    suspend fun getAnimals(monitorLogId: Long): List<Animal>

    suspend fun getAnimalById(id: Long): Animal?

    suspend fun insertAnimal(animal: Animal)

    suspend fun updateAnimal(animal: Animal)

    suspend fun deleteAnimal(animal: Animal)

    suspend fun deleteAnimalById(id: Long)
}