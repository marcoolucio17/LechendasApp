package com.example.lechendasapp.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lechendasapp.data.model.MonitorLog
import com.example.lechendasapp.data.repository.MonitorLogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FormsUiState(
    val climateType: String = "",
    val seasons: String = "",
    val zone: String = "",
    val logType: String = "",
)


fun MonitorLog.toFormsUiState(): FormsUiState = FormsUiState(
    climateType = this.climateType,
    seasons = this.seasons,
    zone = this.zone,
    logType = this.logType,
)

fun FormsUiState.toMonitorLog(): MonitorLog = MonitorLog(
    id = 0, //TODO: como es automático tal vez se pude dejar defautl 0 y que se encargue el DAO

    //TODO: get this values from the user or app
    userId = 0,
    dateMillis = 1,
    gpsCoordinates = "g_",
    location = "l_",

    climateType = this.climateType,
    seasons = this.seasons,
    zone = this.zone,
    logType = this.logType,
)

@HiltViewModel
class FormularioViewModel @Inject constructor(
    private val monitorLogRepository: MonitorLogRepository
) : ViewModel() {
    private val _formsUiState = mutableStateOf(FormsUiState())
    val formsUiState: State<FormsUiState> = _formsUiState

    private val _monitorLogId = mutableLongStateOf(0L)
    val monitorLogId: State<Long> = _monitorLogId

    fun updateUiState(newUi: FormsUiState) {
        _formsUiState.value = newUi
    }

    fun addNewForm(onFormAdded: (Long) -> Unit) {
        viewModelScope.launch {
            val newForm = _formsUiState.value.toMonitorLog()

            //TODO: VALIDATE NOT BLANK FIELDS

            val newId = monitorLogRepository.addMonitorLog(newForm)
            _monitorLogId.longValue = newId

            onFormAdded(newId)
        }
    }
}