package com.example.lechendasapp.viewmodels

import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lechendasapp.data.model.Animal
import com.example.lechendasapp.data.model.Photo
import com.example.lechendasapp.data.repository.AnimalRepository
import com.example.lechendasapp.data.repository.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AnimalUiSate(
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
    private val animalRepository: AnimalRepository,
    private val photoRepository: PhotoRepository
) : ViewModel() {
    private val _animalUiState = mutableStateOf(AnimalUiSate())
    val animalUiState: State<AnimalUiSate> = _animalUiState

    private val _monitorLogId = mutableLongStateOf(0L)
    val monitorLogId: State<Long> = _monitorLogId

    private val _animalId = mutableLongStateOf(0L)
    val animalId: State<Long> = _animalId

    private val _unassociatedPhotos = MutableStateFlow<List<Photo>>(emptyList())
    val unassociatedPhotos: StateFlow<List<Photo>> = _unassociatedPhotos.asStateFlow()

    private val _associatedPhotos = MutableStateFlow<List<Photo>>(emptyList())
    val associatedPhotos: StateFlow<List<Photo>> = _associatedPhotos.asStateFlow()

    init {
        clearUnassociatedPhotos()
        fetchUnassociatedPhotos()
    }

    fun clearUnassociatedPhotos() {
        viewModelScope.launch {
            photoRepository.deletePhotoByNull()
        }
    }

    fun fetchAssociatedPhotosIfNeeded() {
        if (animalId.value != 0L && monitorLogId.value != 0L) {
            Log.d("AnimalViewModel", "Fetching associated photos")
            fetchAssociatedPhotos(monitorLogId.value, animalId.value)
        }
    }

    private fun fetchUnassociatedPhotos() {
        viewModelScope.launch {
            photoRepository.getPhotoStreamByNull()
                .collect { photos ->
                    _unassociatedPhotos.value = photos
                }
        }
    }

    private fun fetchAssociatedPhotos(monitorLogId: Long, animalId: Long) {
        viewModelScope.launch {
            photoRepository.getPhotoStreamByMonitorLogFormsId(monitorLogId, animalId)
                .collect { photos ->
                    _associatedPhotos.value = photos
                }
        }
    }

    fun pickImage(context: Context, launcher: ActivityResultLauncher<Intent>) {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        launcher.launch(intent)
    }

    fun getImage(imagePath: String) {
        viewModelScope.launch {
            photoRepository.insertPhoto(
                Photo(
                    id = 0, // auto-generated
                    formsId = -1,
                    monitorLogId = -1,
                    filePath = imagePath,
                    image = null,
                    description = null
                )
            )
        }
    }

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
        val errors = uiState.errors.toMutableMap() // Mantener errores existentes

        // Validación de campos
        if (uiState.animalType.isBlank()) {
            errors["animalType"] = "El tipo de animal es obligatorio."
        } else {
            errors.remove("animalType") // Eliminar error si es válido
        }

        if (uiState.commonName.isBlank()) {
            errors["commonName"] = "El nombre común es obligatorio."
        } else {
            errors.remove("commonName")
        }

        if (uiState.scientificName.isBlank()) {
            errors["scientificName"] = "El nombre científico es obligatorio."
        } else {
            errors.remove("scientificName")
        }

        if (uiState.quantity.isBlank() || uiState.quantity.toIntOrNull() == null) {
            errors["quantity"] = "La cantidad es obligatoria y debe ser un número válido."
        } else {
            errors.remove("quantity")
        }

        if (uiState.observationType.isBlank()) {
            errors["observationType"] = "El tipo de observación es obligatorio."
        } else {
            errors.remove("observationType")
        }

        if (uiState.transectName.isBlank()) {
            errors["transectName"] = "El nombre del transecto es obligatorio."
        } else {
            errors.remove("transectName")
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
                    val id = animalRepository.insertAnimal(newAnimal)
                    _unassociatedPhotos.value.map { photo ->
                        val updatedPhoto =
                            photo.copy(monitorLogId = _monitorLogId.longValue, formsId = id)
                        photoRepository.updatePhoto(updatedPhoto)
                    }
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
                    _unassociatedPhotos.value.map { photo ->
                        val updatedPhoto =
                            photo.copy(monitorLogId = _monitorLogId.longValue, formsId = animalId.value)
                        photoRepository.updatePhoto(updatedPhoto)
                    }
                }
            }
        }
    }
}
