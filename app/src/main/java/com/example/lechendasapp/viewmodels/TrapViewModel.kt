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
    val installationDate : String = "",
    val lensHeight: String = "",
    val objectiveDistance: String = "",
    val checkList: Map<CheckList, Boolean> = CheckList.entries.associateWith { false },
    val observations: String? = "",
)

fun Trap.toTrapUiState(): TrapUiState = TrapUiState(
    code = this.code,

    cameraName = this.cameraName,
    cameraPlate = this.cameraPlate,
    guayaPlate = this.guayaPlate,
    roadWidth = this.roadWidth.toString(),
    installationDate = this.installationDate,
    lensHeight = this.lensHeight.toString(),
    objectiveDistance = this.objectiveDistance.toString(),
    checkList = this.checkList.split(",").associate {
        CheckList.valueOf(it.trim()) to true
    },

    observations = this.observations,
)

fun TrapUiState.toTrap(): Trap = Trap(
    id = 0, // This can be default / handled by DAO

    monitorLogId = 0, // this is handled with the route

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

    private val _logId = mutableLongStateOf(0L)
    val logId: State<Long> = _logId

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
        if (logId.value != 0L && monitorLogId.value != 0L) {
            Log.d("AnimalViewModel", "Fetching associated photos")
            fetchAssociatedPhotos(monitorLogId.value, logId.value)
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

    fun updateUiState(newUi: TrapUiState) {
        _trapUiState.value = newUi
    }

    fun setMonitorLogId(id: Long) {
        _monitorLogId.longValue = id
    }

    fun setTrapId(id: Long) {
        _logId.longValue = id
        viewModelScope.launch {
            trapRepository.getTrapById(id)
            _trapUiState.value = trapRepository.getTrapById(id)?.toTrapUiState()!!
        }
    }

    fun updateCheckList(check: CheckList, isChecked: Boolean) {
        val updatedList = _trapUiState.value.checkList.toMutableMap().apply {
            this[check] = isChecked
        }
        _trapUiState.value = _trapUiState.value.copy(checkList = updatedList)
    }


    fun addNewLog() {
        if (_logId.longValue == 0L) {
            //Insert new log
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
        } else {
            //Update log
            val newTrap = _trapUiState.value.toTrap().copy(
                id = _logId.longValue,
                monitorLogId = _monitorLogId.longValue
            )
            viewModelScope.launch {
                trapRepository.updateTrap(newTrap)
                _unassociatedPhotos.value.map { photo ->
                    val updatedPhoto =
                        photo.copy(monitorLogId = _monitorLogId.longValue, formsId = logId.value)
                    photoRepository.updatePhoto(updatedPhoto)
                }
            }
        }
    }
}