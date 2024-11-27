package com.example.lechendasapp.viewmodels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lechendasapp.data.model.MonitorLog
import com.example.lechendasapp.data.repository.AnimalRepository
import com.example.lechendasapp.data.repository.ClimateRepository
import com.example.lechendasapp.data.repository.CoverageRepository
import com.example.lechendasapp.data.repository.MonitorLogRepository
import com.example.lechendasapp.data.repository.PhotoRepository
import com.example.lechendasapp.data.repository.TrapRepository
import com.example.lechendasapp.data.repository.VegetationRepository
import com.example.lechendasapp.screens.TipoRegistro
import com.example.lechendasapp.screens.toReadableDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


data class SearchUiState(
    val log: List<MonitorLog> = listOf()
)

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val monitorLogRepository: MonitorLogRepository,
    private val animalRepository: AnimalRepository,
    private val coveragesRepository: CoverageRepository,
    private val vegetationRepository: VegetationRepository,
    private val trapRepository: TrapRepository,
    private val climateRepository: ClimateRepository,
    private val photoRepository: PhotoRepository
) : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery


    private val _searchUiState: StateFlow<SearchUiState> =
        combine(
            monitorLogRepository.getMonitorLogsStream(),
            _searchQuery
        ) { logs, query ->
            val filteredLogs = logs.filter { log ->
                log.logType.contains(query, ignoreCase = true) ||
                        log.id.toString().contains(query, ignoreCase = true) ||
                        log.dateMillis.toReadableDate().contains(query, ignoreCase = true)
            }
            SearchUiState(filteredLogs)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SearchUiState()
        )

    val searchUiState: StateFlow<SearchUiState> = _searchUiState

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun deleteMonitorLog(monitorLogId: Long) {
        viewModelScope.launch {
            monitorLogRepository.deleteMonitorLogById(monitorLogId)

            //get logType from List<MonitorLog> on SearchUiState
            var logType = _searchUiState.value.log.filter { it.id == monitorLogId }.first().logType

            //convert logtype of type string ot counterpart from enumerate class
            val enumValue = TipoRegistro.entries.find { it.displayName == logType }


            //delete all data from logType
            Log.d("SearchViewModel", "Deleting $logType")
            when (enumValue) {
                TipoRegistro.TRANSECTOS -> {
                    animalRepository.deleteAnimalByMonitorLogId(monitorLogId)
                }
                TipoRegistro.BUSQUEDA_LIBRE -> {
                    animalRepository.deleteAnimalByMonitorLogId(monitorLogId)
                }
                TipoRegistro.PUNTO_CONTEO -> {
                    animalRepository.deleteAnimalByMonitorLogId(monitorLogId)
                }
                TipoRegistro.VALIDACION_COBERTURA -> {
                    coveragesRepository.deleteConverageByMonitorLogId(monitorLogId)
                }
                TipoRegistro.PARCELA_VEGETACION -> {
                    vegetationRepository.deleteVegetationByMonitorLogId(monitorLogId)
                }
                TipoRegistro.CAMARAS_TRAMPA -> {
                    trapRepository.deleteTrapByMonitorLogId(monitorLogId)
                }
                TipoRegistro.VARIABLES_CLIMATICAS -> {
                    climateRepository.deleteClimateByMonitorLogId(monitorLogId)
                }
                else -> {
                    Log.d("SearchViewModel", "No se encontro el tipo de registro")
                }
            }

            photoRepository.deletePhotoByMonitorFormsId(monitorLogId)

        }
    }
}