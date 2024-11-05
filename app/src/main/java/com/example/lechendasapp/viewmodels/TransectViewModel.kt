package com.example.lechendasapp.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.lechendasapp.data.model.Animal
import com.example.lechendasapp.data.repository.AnimalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class AnimalUiSate (
    val animalType: String = "",
    val commonName: String = "",
    val scientificName: String = "",
    val quantity: String = "",
    val observationType: String = "",
    val transectName: String = "",
    val observationHeight: String = "",
    val observations: String = "",
)

fun AnimalUiSate.toAnimal() = Animal(
    id = 0,
    monitorLogId = 0,

    animalType = animalType,
    commonName = commonName,
    scientificName = scientificName,
    quantity = quantity.toInt(),
    observationType = observationType,
    transectName = transectName,
    observationHeight = observationHeight,
    observations = observations,
)

fun Animal.toAnimalUiState() = AnimalUiSate(
    animalType = animalType,

    commonName = commonName,
    scientificName = scientificName ?: "",
    quantity = quantity.toString(),
    observationType = observationType,
    transectName = transectName ?: "",
    observationHeight = observationHeight ?: "",
    observations = observations ?: "",
)

@HiltViewModel
class AnimalViewModel @Inject constructor(
    private val animalRepository: AnimalRepository
) : ViewModel(){
    private val _animalUiState = mutableStateOf(AnimalUiSate())
    val animalUiState: State<AnimalUiSate> = _animalUiState

    private val _monitorLogId = mutableLongStateOf(0L)
    val monitorLogId: State<Long> = _monitorLogId

    fun updateUiState(newUiState: AnimalUiSate) {
        _animalUiState.value = newUiState
    }

    fun setMonitorLogId(id: Long) {
        _monitorLogId.longValue = id
    }

    fun saveAnimal() {
        val newAnimal = _animalUiState.value.toAnimal().copy(
            monitorLogId = _monitorLogId.longValue
        )
    }
}
