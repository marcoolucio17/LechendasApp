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
    val rainfall: String = "",
    val maxTemp: String = "",
    val minTemp: String = "",
    val maxHumidity: String = "",
    val minHumidity: String = "",
    val ravineLevel: String = "",
    val observations: String = "",
    val errors: Map<String, String> = emptyMap() // Para almacenar mensajes de error
)


fun Climate.toClimateUiState(): ClimateUiState = ClimateUiState(
    rainfall = this.rainfall.toString(),
    maxTemp = this.maxTemp.toString(),
    minTemp = this.minTemp.toString(),
    maxHumidity = this.maxHumidity.toString(),
    minHumidity = this.minHumidity.toString(),
    ravineLevel = this.ravineLevel.toString(),
)

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
    private val climateRepository: ClimateRepository
) : ViewModel() {
    private val _climateUiState = mutableStateOf(ClimateUiState())
    val climateUiState: State<ClimateUiState> = _climateUiState

    private val _monitorLogId = mutableLongStateOf(0L)
    val monitorLogId: State<Long> = _monitorLogId

    private val _climateId = mutableLongStateOf(0L)
    val climateId: State<Long> = _climateId

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
                    climateRepository.insertClimate(newLog)
                }
            } else {
                //Update new climate
                val newClimate = _climateUiState.value.toClimate().copy(
                    id = _climateId.longValue,
                    monitorLogId = _monitorLogId.longValue
                )
                viewModelScope.launch {
                    climateRepository.updateClimate(newClimate)
                }
            }
        }
    }
}
