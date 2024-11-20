package com.example.lechendasapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.lechendasapp.data.model.Vegetation
import com.example.lechendasapp.data.repository.VegetationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableLongStateOf
import androidx.lifecycle.viewModelScope
import com.example.lechendasapp.screens.CuadranteMain
import com.example.lechendasapp.screens.CuadranteSecond
import com.example.lechendasapp.screens.Habito
import com.example.lechendasapp.screens.SubCuadrante
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
    private val vegetationRepository: VegetationRepository
) : ViewModel() {
    private val _vegetationUiState = mutableStateOf(VegetationUiState())
    val vegetationUiState: State<VegetationUiState> = _vegetationUiState

    private val _monitorLogId = mutableLongStateOf(0L)
    val monitorLogId: State<Long> = _monitorLogId

    private val _vegetationId = mutableLongStateOf(0L)
    val vegetationId: State<Long> = _vegetationId

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
                    vegetationRepository.insertVegetation(newLog)
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
                }
            }
        }
    }
}
