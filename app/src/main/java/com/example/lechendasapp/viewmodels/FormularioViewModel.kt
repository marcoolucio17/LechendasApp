package com.example.lechendasapp.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lechendasapp.data.model.MonitorLog
import com.example.lechendasapp.data.repository.MonitorLogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FormsUiState(
    val form: Form = Form()
)

data class Form(
    val id: Long = 0,
    val userId: Long = 0,

    val dateMillis: Long = 0,
    val gpsCoordinates: String = "",
    val location: String = "",

    val climateType: String = "",
    val seasons: String = "",
    val logType: String = "",

    val observations: String? = null,
    val zone: String = "",
)

fun MonitorLog.toFormsUiState(): FormsUiState = FormsUiState(
    form = this.toForm()
)

fun MonitorLog.toForm(): Form = Form(
    id = this.id,
    userId = this.userId,

    dateMillis = this.dateMillis,
    gpsCoordinates = this.gpsCoordinates,
    location = this.location,

    climateType = this.climateType,
    seasons = this.seasons,
    logType = this.logType,

    zone = this.zone,
)

fun FormsUiState.toMonitorLog(): MonitorLog = MonitorLog(
    id = this.form.id,
    userId = this.form.userId,

    dateMillis = this.form.dateMillis,
    gpsCoordinates = this.form.gpsCoordinates,
    location = this.form.location,

    climateType = this.form.climateType,
    seasons = this.form.seasons,
    logType = this.form.logType,

    zone = this.form.zone,
)

@HiltViewModel
class FormularioViewModel @Inject constructor(
    private val monitorLogRepository: MonitorLogRepository
) : ViewModel() {
    private val _formsUiState = mutableStateOf(FormsUiState())
    val formsUiState: State<FormsUiState> = _formsUiState

    fun updateUiState(newForm: Form) {
        _formsUiState.value = _formsUiState.value.copy(form = newForm)
    }

    fun addNewForm() {
        viewModelScope.launch {
            //get last form id
            monitorLogRepository.getAllMonitorLogs()

            // update form id
            _formsUiState.value = _formsUiState.value.copy(form = _formsUiState.value.form.copy(id = _formsUiState.value.form.id + 1))


            val newForm = _formsUiState.value.toMonitorLog()
            monitorLogRepository.addMonitorLog(newForm)

        }
    }
}