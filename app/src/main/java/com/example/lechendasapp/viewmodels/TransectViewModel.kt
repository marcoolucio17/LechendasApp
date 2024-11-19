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
            _animalUiState.value = animal?.toAnimalUiState()!!
        }
    }

    fun saveAnimal() {
        if (_animalId.longValue == 0L) {
            //Insert new log
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
        } else {
            //Update existing log
            val newAnimal = _animalUiState.value.toAnimal().copy(
                id = _animalId.longValue,
                monitorLogId = _monitorLogId.longValue
            )
            viewModelScope.launch {
                animalRepository.updateAnimal(newAnimal)
                _unassociatedPhotos.value.map { photo ->
                    val updatedPhoto =
                        photo.copy(monitorLogId = _monitorLogId.longValue, formsId = animalId.value)
                    photoRepository.updatePhoto(updatedPhoto)
                }
            }
        }
    }
}
