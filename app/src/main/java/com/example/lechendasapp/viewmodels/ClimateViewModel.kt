package com.example.lechendasapp.viewmodels

import android.util.Log
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

data class ClimateUiState(
    val rainfall: Int = 0,
    val maxTemp: Int = 0,
    val minTemp: Int = 0,
    val maxHumidity: Int = 0,
    val minHumidity: Int = 0,
    val ravineLevel: Int = 0,
    val observations: String = ""
)

fun Climate.toClimateUiState(): ClimateUiState = ClimateUiState(
    rainfall = this.rainfall,
    maxTemp = this.maxTemp,
    minTemp = this.minTemp,
    maxHumidity = this.maxHumidity,
    minHumidity = this.minHumidity,
    ravineLevel = this.ravineLevel,
)

fun ClimateUiState.toClimateLog(): Climate = Climate(
    id = 0, // This can be default or handled by DAO

    monitorLogId = 0, //TODO: should be same as previous

    rainfall = this.rainfall,
    maxTemp = this.maxTemp,
    minTemp = this.minTemp,
    maxHumidity = this.maxHumidity,
    minHumidity = this.minHumidity,
    ravineLevel = this.ravineLevel,
    observations = this.observations,
)

@HiltViewModel
class ClimateViewModel @Inject constructor(
    private val climateRepository: ClimateRepository
) : ViewModel() {
    private var _climateUiState by mutableStateOf(ClimateUiState())
    val climateUiState: ClimateUiState = _climateUiState

    private var _monitorLogId by mutableLongStateOf(0L)
    val monitorLogId: Long = _monitorLogId

    fun updateUiState(newUi: ClimateUiState) {
        _climateUiState = newUi
    }

    fun setMonitorLogId(id: Long) {
        _monitorLogId = id
    }

    fun addNewLog() {
        viewModelScope.launch {
            val newLog = _climateUiState.toClimateLog()

            //update id values
            val new = newLog.copy(monitorLogId = _monitorLogId)

            // TODO: VALIDATE NOT BLANK FIELDS
            Log.d("ClimateViewModel", "Adding new log: $new")

            climateRepository.insertClimate(new)
        }
    }
}