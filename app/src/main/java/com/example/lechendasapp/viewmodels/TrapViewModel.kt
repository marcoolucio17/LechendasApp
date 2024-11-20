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
import com.example.lechendasapp.data.model.Photo
import com.example.lechendasapp.data.model.Trap
import com.example.lechendasapp.data.repository.PhotoRepository
import com.example.lechendasapp.data.repository.TrapRepository
import com.example.lechendasapp.screens.CheckList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class TrapUiState(
    val code: String = "",
    val cameraName: String = "",
    val cameraPlate: String = "",
    val guayaPlate: String = "",
    val roadWidth: String = "",
    val installationDate: String = "",
    val lensHeight: String = "",
    val objectiveDistance: String = "",
    val checkList: Map<CheckList, Boolean> = CheckList.entries.associateWith { false },
    val observations: String = "",
    val errors: Map<String, String> = emptyMap() // Para almacenar mensajes de error
) {
    companion object {
        fun empty() = TrapUiState()
    }
}

fun Trap.toTrapUiState(): TrapUiState = TrapUiState(
    code = this.code,
    cameraName = this.cameraName,
    cameraPlate = this.cameraPlate,
    guayaPlate = this.guayaPlate,
    roadWidth = this.roadWidth.toString(),
    installationDate = this.installationDate,
    lensHeight = this.lensHeight.toString(),
    objectiveDistance = this.objectiveDistance.toString(),
    observations = this.observations.toString(),
    checkList = this.checkList.split(",").associate {
        CheckList.valueOf(it.trim()) to true
    }

)

fun TrapUiState.toTrap(): Trap = Trap(
    id = 0,
    monitorLogId = 0,
    code = this.code,
    cameraName = this.cameraName,
    cameraPlate = this.cameraPlate,
    guayaPlate = this.guayaPlate,
    roadWidth = this.roadWidth.toIntOrNull() ?: 0,
    installationDate = this.installationDate,
    lensHeight = this.lensHeight.toIntOrNull() ?: 0,
    objectiveDistance = this.objectiveDistance.toIntOrNull() ?: 0,
    checkList = this.checkList.filterValues { it }.keys.joinToString(","),
    observations = this.observations,
)

@HiltViewModel
class TrapViewModel @Inject constructor(
    private val trapRepository: TrapRepository,
    private val photoRepository: PhotoRepository
) : ViewModel() {
    private val _trapUiState = mutableStateOf(TrapUiState())
    val trapUiState: State<TrapUiState> = _trapUiState

    private val _monitorLogId = mutableLongStateOf(0L)
    val monitorLogId: State<Long> = _monitorLogId

    private val _trapId = mutableLongStateOf(0L)
    val trapId: State<Long> = _trapId

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
        if (trapId.value != 0L && monitorLogId.value != 0L) {
            Log.d("AnimalViewModel", "Fetching associated photos")
            fetchAssociatedPhotos(monitorLogId.value, trapId.value)
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

    fun updateUiState(newUi: TrapUiState) {
        _trapUiState.value = newUi
    }

    fun setMonitorLogId(id: Long) {
        _monitorLogId.longValue = id
    }

    fun setTrapId(id: Long) {
        _trapId.longValue = id
        viewModelScope.launch {
            val trap = trapRepository.getTrapById(id)
            _trapUiState.value = trap?.toTrapUiState() ?: TrapUiState.empty()
        }
    }

    fun resetForm() {
        _trapUiState.value = TrapUiState.empty()
    }

    fun validateFields(): Boolean {
        val uiState = _trapUiState.value
        val errors = mutableMapOf<String, String>()

        if (uiState.code.isBlank()) errors["code"] = "El código es obligatorio."
        if (uiState.cameraName.isBlank()) errors["cameraName"] = "El nombre de la cámara es obligatorio."
        if (uiState.cameraPlate.isBlank()) errors["cameraPlate"] = "La placa de la cámara es obligatoria."
        if (uiState.guayaPlate.isBlank()) errors["guayaPlate"] = "La placa de la guaya es obligatoria."
        if (uiState.roadWidth.isBlank()) errors["roadWidth"] = "El ancho de la carretera es obligatorio."
        if (uiState.installationDate.isBlank()) errors["installationDate"] = "La fecha de instalación es obligatoria."
        if (uiState.lensHeight.isBlank()) errors["lensHeight"] = "La altura de la lente es obligatoria."
        if (uiState.objectiveDistance.isBlank()) errors["objectiveDistance"] = "La distancia al objetivo es obligatoria."
        if (uiState.checkList.none { it.value }) {
            errors["checkList"] = "Debe seleccionar al menos un elemento."
        }

        _trapUiState.value = uiState.copy(errors = errors)

        return errors.isEmpty()
    }

    fun updateCheckList(check: CheckList, isChecked: Boolean) {
        // Actualizar solo la casilla seleccionada, dejando el resto intacto
        val updatedCheckList = _trapUiState.value.checkList.toMutableMap().apply {
            this[check] = isChecked  // Cambia el valor de la casilla específica
        }
        _trapUiState.value = _trapUiState.value.copy(checkList = updatedCheckList)
    }



    fun addNewLog() {
        if (validateFields()) { // Verifica que los campos no estén vacíos
            if (_trapId.longValue == 0L) {
                // Insertar nuevo registro
                val newTrap = _trapUiState.value.toTrap().copy(
                    monitorLogId = _monitorLogId.longValue
                )
                viewModelScope.launch {
                    val id = trapRepository.insertTrap(newTrap)
                    _unassociatedPhotos.value.map { photo ->
                        val updatedPhoto =
                            photo.copy(monitorLogId = _monitorLogId.longValue, formsId = id)
                        photoRepository.updatePhoto(updatedPhoto)
                    }
                }
                resetForm()
            } else {
                // Actualizar registro existente
                val updatedTrap = _trapUiState.value.toTrap().copy(
                    id = _trapId.longValue,
                    monitorLogId = _monitorLogId.longValue
                )
                viewModelScope.launch {
                    trapRepository.updateTrap(updatedTrap)
                    _unassociatedPhotos.value.map { photo ->
                        val updatedPhoto =
                            photo.copy(monitorLogId = _monitorLogId.longValue, formsId = trapId.value)
                        photoRepository.updatePhoto(updatedPhoto)
                    }
                }
            }
        }
    }
}
