package com.example.lechendasapp.viewmodels

import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import com.example.lechendasapp.data.model.Vegetation
import com.example.lechendasapp.data.repository.VegetationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableLongStateOf
import androidx.lifecycle.viewModelScope
import com.example.lechendasapp.data.model.Photo
import com.example.lechendasapp.data.repository.PhotoRepository
import com.example.lechendasapp.screens.CuadranteMain
import com.example.lechendasapp.screens.CuadranteSecond
import com.example.lechendasapp.screens.Habito
import com.example.lechendasapp.screens.SubCuadrante
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class VegetationUiState(
    val code: String = "",
    val quadrant: String = "",
    val quadrantSecond: String = "",
    val subQuadrant: String = "",
    val growthHabit: String = "",
    val commonName: String = "",
    val scientificName: String? = "",
    val plate: String = "",
    val circumference: String = "",
    val distance: String = "",
    val height: String = "",
    val observations: String? = "",
    val errors: Map<String, String> = emptyMap() // Para almacenar mensajes de error
) {
    companion object {
        fun empty() = VegetationUiState()
    }
}

fun Vegetation.toVegetationUiState(): VegetationUiState = VegetationUiState(
    code = this.code,
    quadrant = this.quadrant.substring(0, 1),
    quadrantSecond = this.quadrant.substring(1),
    subQuadrant = this.subQuadrant,
    growthHabit = this.growthHabit,
    commonName = this.commonName,
    scientificName = this.scientificName,
    plate = this.plate,
    circumference = this.circumference.toString(),
    distance = this.distance.toString(),
    height = this.height.toString(),
    observations = this.observations
)

fun VegetationUiState.toVegetation(): Vegetation = Vegetation(
    id = 0,
    monitorLogId = 0,
    code = this.code,
    quadrant = this.quadrant + this.quadrantSecond,
    subQuadrant = this.subQuadrant,
    growthHabit = this.growthHabit,
    commonName = this.commonName,
    scientificName = this.scientificName,
    plate = this.plate,
    circumference = this.circumference.toIntOrNull() ?: 0, // Manejo de errores al convertir
    distance = this.distance.toIntOrNull() ?: 0, // Manejo de errores al convertir
    height = this.height.toIntOrNull() ?: 0, // Manejo de errores al convertir
    observations = this.observations
)


@HiltViewModel
class VegetationViewModel @Inject constructor(
    private val vegetationRepository: VegetationRepository,
    private val photoRepository: PhotoRepository
) : ViewModel() {
    private val _vegetationUiState = mutableStateOf(VegetationUiState())
    val vegetationUiState: State<VegetationUiState> = _vegetationUiState

    private val _monitorLogId = mutableLongStateOf(0L)
    val monitorLogId: State<Long> = _monitorLogId

    private val _vegetationId = mutableLongStateOf(0L)
    val vegetationId: State<Long> = _vegetationId

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
        if (vegetationId.value != 0L && monitorLogId.value != 0L) {
            Log.d("AnimalViewModel", "Fetching associated photos")
            fetchAssociatedPhotos(monitorLogId.value, vegetationId.value)
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


    fun updateUiState(newUi: VegetationUiState) {
        _vegetationUiState.value = newUi
    }

    fun setMonitorLogId(id: Long) {
        _monitorLogId.longValue = id
    }

    fun setVegetationId(id: Long) {
        _vegetationId.longValue = id
        viewModelScope.launch {
            val vegetation = vegetationRepository.getVegetationById(id)
            _vegetationUiState.value = vegetation?.toVegetationUiState()!!
        }
    }

    fun resetForm() {
        _vegetationUiState.value = VegetationUiState.empty()
    }

    fun validateFields(): Boolean {
        val uiState = _vegetationUiState.value
        val errors = mutableMapOf<String, String>()

        if (uiState.code.isBlank()) errors["code"] = "El código es obligatorio."
        if (uiState.quadrant.isBlank()) errors["quadrant"] = "El cuadrante es obligatorio."
        if (uiState.subQuadrant.isBlank()) errors["subQuadrant"] = "El subCuadrante es obligatorio."
        if (uiState.growthHabit.isBlank()) errors["growthHabit"] = "El hábito de crecimiento es obligatorio."
        if (uiState.commonName.isBlank()) errors["commonName"] = "El nombre común es obligatorio."
        if (uiState.plate.isBlank()) errors["plate"] = "La placa es obligatoria."
        if (uiState.circumference.isBlank() || uiState.circumference.toIntOrNull() == null) {
            errors["circumference"] = "La circunferencia debe ser un número válido."
        }
        if (uiState.distance.isBlank() || uiState.distance.toIntOrNull() == null) {
            errors["distance"] = "La distancia debe ser un número válido."
        }
        if (uiState.height.isBlank() || uiState.height.toIntOrNull() == null) {
            errors["height"] = "La altura debe ser un número válido."
        }

        _vegetationUiState.value = uiState.copy(errors = errors)

        return errors.isEmpty()
    }

    fun addNewLog() {
        if (validateFields()) {
            if (_vegetationId.longValue == 0L) {
                // Insert new log
                val newLog = _vegetationUiState.value.toVegetation().copy(
                    monitorLogId = _monitorLogId.longValue
                )
                viewModelScope.launch {
                    val id = vegetationRepository.insertVegetation(newLog)
                    _unassociatedPhotos.value.map { photo ->
                        val updatedPhoto =
                            photo.copy(monitorLogId = _monitorLogId.longValue, formsId = id)
                        photoRepository.updatePhoto(updatedPhoto)
                    }
                }
                resetForm()
            } else {
                // Update existing log
                val updatedLog = _vegetationUiState.value.toVegetation().copy(
                    id = _vegetationId.longValue,
                    monitorLogId = _monitorLogId.longValue
                )
                viewModelScope.launch {
                    vegetationRepository.updateVegetation(updatedLog)
                    _unassociatedPhotos.value.map { photo ->
                        val updatedPhoto =
                            photo.copy(monitorLogId = _monitorLogId.longValue, formsId = vegetationId.value)
                        photoRepository.updatePhoto(updatedPhoto)
                    }
                }
            }
        }
    }
}
