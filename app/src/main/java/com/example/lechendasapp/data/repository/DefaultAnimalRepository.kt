package com.example.lechendasapp.data.repository

import javax.inject.Singleton
import com.example.lechendasapp.data.model.Animal
import com.example.lechendasapp.data.source.local.AnimalDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@Singleton
class DefaultAnimalRepository @Inject constructor(
    private val localDataSource: AnimalDao,
) : AnimalRepository {
    override fun getAnimalsStream(): Flow<List<Animal>> {
        return localDataSource.observeAll().map { animals ->
            animals.toExternal()
        }
    }

    override fun getAnimalsStreamByMonitorLogId(monitorLogId: Long): Flow<List<Animal>> {
        return localDataSource.observeByMonitorLogId(monitorLogId).map { animals ->
            animals.toExternal()
        }
    }

    override fun getIndividualAnimalStream(animalId: Long): Flow<Animal> {
        return localDataSource.observeById(animalId).map { it.toExternal() }
    }

    override suspend fun getAnimals(monitorLogId: Long): List<Animal> {
        return localDataSource.getAll().toExternal()
    }

    override suspend fun getAnimalById(id: Long): Animal? {
        return localDataSource.getById(id)?.toExternal()
    }

    override suspend fun insertAnimal(animal: Animal) : Long {
        return localDataSource.insert(animal.toLocal())
    }

    override suspend fun updateAnimal(animal: Animal) {
        localDataSource.update(animal.toLocal())
    }

    override suspend fun deleteAnimal(animal: Animal) {
        localDataSource.delete(animal.toLocal())
    }

    override suspend fun deleteAnimalById(id: Long) {
        localDataSource.deleteById(id)
    }
}