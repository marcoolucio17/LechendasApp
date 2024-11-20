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
import com.example.lechendasapp.data.model.Coverage
import com.example.lechendasapp.data.model.Photo
import com.example.lechendasapp.data.repository.CoverageRepository
import com.example.lechendasapp.data.repository.PhotoRepository
import com.example.lechendasapp.screens.CoverageOptions
import com.example.lechendasapp.screens.DisturbanceOptions
import com.example.lechendasapp.screens.SINO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CoverageUiState(
    val code: String = "",
    val tracking: Map<SINO, Boolean> = SINO.entries.associateWith { false },
    val change: Map<SINO, Boolean> = SINO.entries.associateWith { false },
    val coverage: Map<CoverageOptions, Boolean> = CoverageOptions.entries.associateWith { false },
    val cropType: String = "",
    val disturbance: Map<DisturbanceOptions, Boolean> = DisturbanceOptions.entries.associateWith { false },
    val observations: String? = "",
    val errors: Map<String, String> = emptyMap() // Para almacenar mensajes de error
) {
    companion object {
        fun empty() = CoverageUiState()
    }
}


fun Coverage.toCoverageUiState(): CoverageUiState = CoverageUiState(
    code = this.code,
    tracking = SINO.entries.associateWith { it.name == this.tracking },
    change = SINO.entries.associateWith { it.name == this.change },
    coverage = CoverageOptions.entries.associateWith { it.name == this.coverage },
    cropType = this.cropType,
    disturbance = DisturbanceOptions.entries.associateWith { it.name == this.disturbance },
    observations = this.observations
)

fun CoverageUiState.toCoverage(): Coverage = Coverage(
    id = 0,
    monitorLogId = 0,

    code = this.code,
    tracking = this.tracking.filterValues { it }.keys.firstOrNull()?.name ?: "",
    change = this.change.filterValues { it }.keys.firstOrNull()?.name ?: "",
    coverage = this.coverage.filterValues { it }.keys.firstOrNull()?.name ?: "",
    cropType = this.cropType,
    disturbance = this.disturbance.filterValues { it }.keys.firstOrNull()?.name ?: "",
    observations = this.observations
)

@HiltViewModel
class CoverageViewModel @Inject constructor(
    private val coverageRepository: CoverageRepository,
    private val photoRepository: PhotoRepository
) : ViewModel() {

    private val _coverageUiState = mutableStateOf(CoverageUiState())
    val coverageUiState: State<CoverageUiState> = _coverageUiState

    private val _monitorLogId = mutableLongStateOf(0L)
    val monitorLogId: State<Long> = _monitorLogId

    private val _coverageId = mutableLongStateOf(0L)
    val coverageId: State<Long> = _coverageId

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
        if (coverageId.value != 0L && monitorLogId.value != 0L) {
            Log.d("AnimalViewModel", "Fetching associated photos")
            fetchAssociatedPhotos(monitorLogId.value, coverageId.value)
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

    fun updateUiState(newUiState: CoverageUiState) {
        _coverageUiState.value = newUiState
    }

    fun setMonitorLogId(id: Long) {
        _monitorLogId.longValue = id
    }

    fun setCoverageId(id: Long) {
        _coverageId.longValue = id
        viewModelScope.launch {
            val coverage = coverageRepository.getConverageById(id)
            _coverageUiState.value = coverage?.toCoverageUiState()!!
        }
    }

    fun resetForm() {
        _coverageUiState.value = CoverageUiState.empty()
    }

    fun validateFields(): Boolean {
        val uiState = _coverageUiState.value
        val errors = mutableMapOf<String, String>()

        // Validación del código
        if (uiState.code.isBlank()) {
            errors["code"] = "El código es obligatorio."
        }

        // Validación de tracking (Seguimiento)
        if (!uiState.tracking.containsValue(true)) {
            errors["tracking"] = "Debe seleccionar una opción de seguimiento."
        }

        // Validación de change (Cambio)
        if (!uiState.change.containsValue(true)) {
            errors["change"] = "Debe seleccionar una opción de cambio."
        }

        // Validación de coverage (Cobertura)
        if (!uiState.coverage.containsValue(true)) {
            errors["coverage"] = "Debe seleccionar una opción de cobertura."
        }

        // Validación de cropType
        if (uiState.cropType.isBlank()) {
            errors["cropType"] = "El tipo de cultivo es obligatorio."
        }

        // Validación de disturbance (Disturbio)
        if (!uiState.disturbance.containsValue(true)) {
            errors["disturbance"] = "Debe seleccionar una opción de disturbio."
        }

        _coverageUiState.value = uiState.copy(errors = errors)
        return errors.isEmpty()
    }

    fun updateCode(newCode: String) {
        _coverageUiState.value = _coverageUiState.value.copy(
            code = newCode,
            errors = _coverageUiState.value.errors - "code" // Elimina el error asociado, si existe
        )
    }

    fun updateTrackingOption(option: SINO) {
        val updatedTracking = SINO.entries.associateWith { it == option }
        _coverageUiState.value = _coverageUiState.value.copy(
            tracking = updatedTracking,
            errors = _coverageUiState.value.errors - "tracking"
        )
    }

    fun updateChangeOption(option: SINO) {
        val updatedChange = SINO.entries.associateWith { it == option }
        _coverageUiState.value = _coverageUiState.value.copy(
            change = updatedChange,
            errors = _coverageUiState.value.errors - "change"
        )
    }

    fun updateCoverageOption(option: CoverageOptions) {
        val updatedCoverage = CoverageOptions.entries.associateWith { it == option }
        _coverageUiState.value = _coverageUiState.value.copy(
            coverage = updatedCoverage,
            errors = _coverageUiState.value.errors - "coverage"
        )
    }

    fun updateCropType(newCropType: String) {
        _coverageUiState.value = _coverageUiState.value.copy(
            cropType = newCropType,
            errors = _coverageUiState.value.errors - "cropType" // Elimina el error asociado, si existe
        )
    }


    fun updateDisturbanceOption(option: DisturbanceOptions) {
        val updatedDisturbance = DisturbanceOptions.entries.associateWith { it == option }
        _coverageUiState.value = _coverageUiState.value.copy(
            disturbance = updatedDisturbance,
            errors = _coverageUiState.value.errors - "disturbance"
        )
    }

    fun saveCoverage() {
        if (validateFields()) {
            if (_coverageId.longValue == 0L) {
                //Insert new coverage
                val newCoverage = _coverageUiState.value.toCoverage().copy(
                    monitorLogId = _monitorLogId.longValue
                )
                viewModelScope.launch {
                    val id = coverageRepository.insertConverage(newCoverage)
                    _unassociatedPhotos.value.map { photo ->
                        val updatedPhoto =
                            photo.copy(monitorLogId = _monitorLogId.longValue, formsId = id)
                        photoRepository.updatePhoto(updatedPhoto)
                    }
                }
                resetForm()
            } else {
                //Update coverage
                val newCoverage = _coverageUiState.value.toCoverage().copy(
                    id = _coverageId.longValue,
                    monitorLogId = _monitorLogId.longValue
                )
                viewModelScope.launch {
                    coverageRepository.updateConverage(newCoverage)
                    _unassociatedPhotos.value.map { photo ->
                        val updatedPhoto =
                            photo.copy(monitorLogId = _monitorLogId.longValue, formsId = coverageId.value)
                        photoRepository.updatePhoto(updatedPhoto)
                    }
                }
            }
        }
    }
}
