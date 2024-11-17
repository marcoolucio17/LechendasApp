package com.example.lechendasapp.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.lechendasapp.data.model.Animal
import com.example.lechendasapp.data.repository.AnimalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

data class AnimalUiSate (
    val animalType: String = "",
    val commonName: String = "",
    val scientificName: String = "",
    val quantity: String = "",
    val observationType: String = "",
    val transectName: String = "",
    val observationHeight: String = "",
    val observations: String = "",
    val errors: Map<String, String> = emptyMap() // Para almacenar mensajes de error
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
) : ViewModel() {
    private val _animalUiState = mutableStateOf(AnimalUiSate())
    val animalUiState: State<AnimalUiSate> = _animalUiState

    private val _monitorLogId = mutableLongStateOf(0L)
    val monitorLogId: State<Long> = _monitorLogId

    private val _animalId = mutableLongStateOf(0L)
    val animalId: State<Long> = _animalId

    private val _errorMessage = mutableStateOf("")
    val errorMessage: State<String> = _errorMessage

    fun updateUiState(newUiState: AnimalUiSate) {
        _animalUiState.value = newUiState
    }

    fun setMonitorLogId(id: Long) {
        _monitorLogId.longValue = id
    }

    fun setAnimalId(id: Long) {
        _animalId.longValue = id
        viewModelScope.launch {
            val animal = animalRepository.getAnimalById(id)
            _animalUiState.value = animal?.toAnimalUiState() ?: AnimalUiSate()
        }
    }

    fun resetForm() {
        _animalUiState.value = AnimalUiSate()
    }

    fun validateFields(): Boolean {
        val uiState = _animalUiState.value
        val errors = mutableMapOf<String, String>()

        if (uiState.animalType.isBlank()) {
            errors["animalType"] = "El tipo de animal es obligatorio."
        }

        if (uiState.commonName.isBlank()) {
            errors["commonName"] = "El nombre común es obligatorio."
        }

        if (uiState.scientificName.isBlank()) {
            errors["scientificName"] = "El nombre científico es obligatorio."
        }

        if (uiState.quantity.isBlank() || uiState.quantity.toIntOrNull() == null) {
            errors["quantity"] = "La cantidad es obligatoria y debe ser un número válido."
        }

        if (uiState.observationType.isBlank()) {
            errors["observationType"] = "El tipo de observación es obligatorio."
        }

        if (uiState.transectName.isBlank()) {
            errors["transectName"] = "El nombre del transecto es obligatorio."
        }

        _animalUiState.value = uiState.copy(errors = errors)
        return errors.isEmpty()
    }

    fun updateAnimalType(newAnimalType: String) {
        _animalUiState.value = _animalUiState.value.copy(
            animalType = newAnimalType,
            errors = _animalUiState.value.errors - "animalType"
        )
    }

    fun updateCommonName(newCommonName: String) {
        _animalUiState.value = _animalUiState.value.copy(
            commonName = newCommonName,
            errors = _animalUiState.value.errors - "commonName"
        )
    }

    fun updateScientificName(newScientificName: String) {
        _animalUiState.value = _animalUiState.value.copy(
            scientificName = newScientificName,
            errors = _animalUiState.value.errors - "scientificName"
        )
    }

    fun updateQuantity(newQuantity: String) {
        _animalUiState.value = _animalUiState.value.copy(
            quantity = newQuantity,
            errors = _animalUiState.value.errors - "quantity"
        )
    }

    fun updateObservationType(newObservationType: String) {
        _animalUiState.value = _animalUiState.value.copy(
            observationType = newObservationType,
            errors = _animalUiState.value.errors - "observationType"
        )
    }

    fun updateTransectName(newTransectName: String) {
        _animalUiState.value = _animalUiState.value.copy(
            transectName = newTransectName,
            errors = _animalUiState.value.errors - "transectName"
        )
    }

    fun updateObservationHeight(newObservationHeight: String) {
        _animalUiState.value = _animalUiState.value.copy(
            observationHeight = newObservationHeight
        )
    }

    fun updateObservations(newObservations: String) {
        _animalUiState.value = _animalUiState.value.copy(
            observations = newObservations
        )
    }

    fun saveAnimal() {
        if (validateFields()) {
            if (_animalId.longValue == 0L) {
                // Insertar nuevo registro
                val newAnimal = _animalUiState.value.toAnimal().copy(
                    monitorLogId = _monitorLogId.longValue
                )
                viewModelScope.launch {
                    animalRepository.insertAnimal(newAnimal)
                }
                resetForm()
            } else {
                // Actualizar registro existente
                val updatedAnimal = _animalUiState.value.toAnimal().copy(
                    id = _animalId.longValue,
                    monitorLogId = _monitorLogId.longValue
                )
                viewModelScope.launch {
                    animalRepository.updateAnimal(updatedAnimal)
                }
            }
        }
    }
}
