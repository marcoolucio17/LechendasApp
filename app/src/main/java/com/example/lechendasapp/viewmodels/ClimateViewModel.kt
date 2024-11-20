package com.example.lechendasapp.viewmodels

import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableLongStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lechendasapp.data.model.Climate
import com.example.lechendasapp.data.repository.ClimateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.lechendasapp.data.model.Photo
import com.example.lechendasapp.data.repository.PhotoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ClimateUiState(
    val rainfall: String = "",
    val maxTemp: String = "",
    val minTemp: String = "",
    val maxHumidity: String = "",
    val minHumidity: String = "",
    val ravineLevel: String = "",
    val observations: String = "",
    val errors: Map<String, String> = emptyMap() // Para almacenar mensajes de error
) {
    companion object {
        fun empty() = ClimateUiState()
    }
}


fun Climate.toClimateUiState(): ClimateUiState = ClimateUiState(
    rainfall = this.rainfall.toString(),
    maxTemp = this.maxTemp.toString(),
    minTemp = this.minTemp.toString(),
    maxHumidity = this.maxHumidity.toString(),
    minHumidity = this.minHumidity.toString(),
    ravineLevel = this.ravineLevel.toString(),
    observations = this.observations.toString()
)


/*TODO: falta vlaidar que sean números, la app crashes porque te deja insertar un string y enviarlos*/
fun ClimateUiState.toClimate(): Climate = Climate(
    id = 0, // This can be default or handled by DAO

    monitorLogId = 0,

    rainfall = this.rainfall.toInt(),
    maxTemp = this.maxTemp.toInt(),
    minTemp = this.minTemp.toInt(),
    maxHumidity = this.maxHumidity.toInt(),
    minHumidity = this.minHumidity.toInt(),
    ravineLevel = this.ravineLevel.toInt(),
    observations = this.observations,
)

@HiltViewModel
class ClimateViewModel @Inject constructor(
    private val climateRepository: ClimateRepository,
    private val photoRepository: PhotoRepository
) : ViewModel() {
    private val _climateUiState = mutableStateOf(ClimateUiState())
    val climateUiState: State<ClimateUiState> = _climateUiState

    private val _monitorLogId = mutableLongStateOf(0L)
    val monitorLogId: State<Long> = _monitorLogId

    private val _climateId = mutableLongStateOf(0L)
    val climateId: State<Long> = _climateId

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
        if (climateId.value != 0L && monitorLogId.value != 0L) {
            Log.d("AnimalViewModel", "Fetching associated photos")
            fetchAssociatedPhotos(monitorLogId.value, climateId.value)
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

    fun updateUiState(newUi: ClimateUiState) {
        _climateUiState.value = newUi
    }

    fun setMonitorLogId(id: Long) {
        _monitorLogId.longValue = id
    }

    fun setClimateId(id: Long) {
        _climateId.longValue = id
        viewModelScope.launch {
            val climate = climateRepository.getClimateById(id)
            _climateUiState.value = climate?.toClimateUiState()!!
        }
    }

    fun resetForm() {
        _climateUiState.value = ClimateUiState.empty()
    }

    fun validateFields(): Boolean {
        val uiState = _climateUiState.value
        val errors = mutableMapOf<String, String>()

        if (uiState.rainfall.isBlank()) errors["rainfall"] = "Este campo es obligatorio."
        if (uiState.maxTemp.isBlank()) errors["maxTemp"] = "Este campo es obligatorio."
        if (uiState.minTemp.isBlank()) errors["minTemp"] = "Este campo es obligatorio."
        if (uiState.maxHumidity.isBlank()) errors["maxHumidity"] = "Este campo es obligatorio."
        if (uiState.minHumidity.isBlank()) errors["minHumidity"] = "Este campo es obligatorio."
        if (uiState.ravineLevel.isBlank()) errors["ravineLevel"] = "Este campo es obligatorio."

        _climateUiState.value = uiState.copy(errors = errors)

        return errors.isEmpty()
    }


    fun addNewLog() {
        if (validateFields()) { // Verifica que los campos no estén vacíos
            if (_climateId.longValue == 0L) {
                //Insert new climate
                val newLog = _climateUiState.value.toClimate().copy(
                    monitorLogId = _monitorLogId.longValue
                )
                viewModelScope.launch {
                    val id = climateRepository.insertClimate(newLog)
                    _unassociatedPhotos.value.map { photo ->
                        val updatedPhoto =
                            photo.copy(monitorLogId = _monitorLogId.longValue, formsId = id)
                        photoRepository.updatePhoto(updatedPhoto)
                    }
                }
                resetForm()
            } else {
                //Update new climate
                val newClimate = _climateUiState.value.toClimate().copy(
                    id = _climateId.longValue,
                    monitorLogId = _monitorLogId.longValue
                )
                viewModelScope.launch {
                    climateRepository.updateClimate(newClimate)
                    _unassociatedPhotos.value.map { photo ->
                        val updatedPhoto =
                            photo.copy(monitorLogId = _monitorLogId.longValue, formsId = climateId.value)
                        photoRepository.updatePhoto(updatedPhoto)
                    }
                }
            }
        }
    }
}
