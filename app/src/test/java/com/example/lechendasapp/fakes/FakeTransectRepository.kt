// FakeAnimalRepository.kt
package com.example.lechendasapp.fakes

import com.example.lechendasapp.data.model.Animal
import com.example.lechendasapp.data.repository.AnimalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class FakeTransectRepository : AnimalRepository {

    private val animals = mutableListOf<Animal>()
    var lastInsertedAnimal: Animal? = null

    override fun getAnimalsStream(): Flow<List<Animal>> {
        return flowOf(animals)
    }

    override fun getAnimalsStreamByMonitorLogId(monitorLogId: Long): Flow<List<Animal>> {
        // Filtra los animales por monitorLogId y retorna un flujo de la lista filtrada
        val filteredAnimals = animals.filter { it.monitorLogId == monitorLogId }
        return flowOf(filteredAnimals)
    }

    override fun getIndividualAnimalStream(animalId: Long): Flow<Animal> {
        // Busca el animal por su ID y devuelve un flujo que emite el animal encontrado
        val animal = animals.find { it.id == animalId }
        return flow {
            if (animal != null) emit(animal)
        }
    }

    override suspend fun getAnimals(monitorLogId: Long): List<Animal> {
        // Retorna una lista de animales filtrada por monitorLogId
        return animals.filter { it.monitorLogId == monitorLogId }
    }

    override suspend fun getAnimalById(id: Long): Animal? {
        return animals.find { it.id == id }
    }

    override suspend fun insertAnimal(animal: Animal) {
        // Almacena el animal y actualiza el último insertado
        lastInsertedAnimal = animal
        animals.add(animal)
    }

    suspend fun addAnimal(animal: Animal) {
        // También podríamos usar insertAnimal si el comportamiento es el mismo
        insertAnimal(animal)
    }

    override suspend fun updateAnimal(animal: Animal) {
        val index = animals.indexOfFirst { it.id == animal.id }
        if (index != -1) {
            animals[index] = animal
        }
    }

    override suspend fun deleteAnimal(animal: Animal) {
        animals.remove(animal)
    }

    override suspend fun deleteAnimalById(id: Long) {
        // Busca el animal por id y elimínalo si existe
        animals.removeIf { it.id == id }
    }
}
