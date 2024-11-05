package com.example.lechendasapp.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lechendasapp.data.model.Coverage
import com.example.lechendasapp.data.repository.CoverageRepository
import com.example.lechendasapp.screens.CoverageOptions
import com.example.lechendasapp.screens.DisturbanceOptions
import com.example.lechendasapp.screens.SINO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CoverageUiState(
    val code: String = "",

    val tracking: Map<SINO, Boolean> = SINO.entries.associateWith { false },
    val change: Map<SINO, Boolean> = SINO.entries.associateWith { false },
    val coverage: Map<CoverageOptions, Boolean> = CoverageOptions.entries.associateWith { false },
    val cropType: String = "",
    val disturbance: Map<DisturbanceOptions, Boolean> = DisturbanceOptions.entries.associateWith { false },
    val observations: String? = ""
)

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
    id = 0, // This would typically be set or handled by the database
    monitorLogId = 0, // Assume handled outside or via ViewModel in context

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
    private val coverageRepository: CoverageRepository
) : ViewModel() {

    private val _coverageUiState = mutableStateOf(CoverageUiState())
    val coverageUiState: State<CoverageUiState> = _coverageUiState

    private val _monitorLogId = mutableLongStateOf(0L)
    val monitorLogId: State<Long> = _monitorLogId

    fun updateUiState(newUiState: CoverageUiState) {
        _coverageUiState.value = newUiState
    }

    fun setMonitorLogId(id: Long) {
        _monitorLogId.longValue = id
    }

    fun updateTrackingOption(option: SINO) {
        val updatedTracking = SINO.entries.associateWith { it == option }
        _coverageUiState.value = _coverageUiState.value.copy(tracking = updatedTracking)
    }

    fun updateChangeOption(option: SINO) {
        val updatedChange = SINO.entries.associateWith { it == option }
        _coverageUiState.value = _coverageUiState.value.copy(change = updatedChange)
    }

    fun updateCoverageOption(option: CoverageOptions) {
        val updatedCoverage = CoverageOptions.entries.associateWith { it == option }
        _coverageUiState.value = _coverageUiState.value.copy(coverage = updatedCoverage)
    }

    fun updateDisturbanceOption(option: DisturbanceOptions) {
        val updatedDisturbance = DisturbanceOptions.entries.associateWith { it == option }
        _coverageUiState.value = _coverageUiState.value.copy(disturbance = updatedDisturbance)
    }

    fun saveCoverage() {
        viewModelScope.launch {
            val newCoverage = _coverageUiState.value.toCoverage().copy(
                monitorLogId = _monitorLogId.longValue
            )

            // Insert newCoverage into repository (assuming suspend function)
            coverageRepository.insertConverage(newCoverage)
        }
    }
}