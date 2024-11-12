package com.example.lechendasapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lechendasapp.data.model.Vegetation
import com.example.lechendasapp.data.repository.VegetationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchVegetationUiState(
    val vegetation: List<Vegetation> = listOf()
)

@HiltViewModel
class SearchVegetationViewModel @Inject constructor(
    private val vegetationRepository: VegetationRepository
) : ViewModel() {
    private val _monitorLogId = MutableStateFlow(-1L)
    val monitorLogId: StateFlow<Long> = _monitorLogId

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _searchVegetationUiState: StateFlow<SearchVegetationUiState> =
        _monitorLogId.flatMapLatest { id ->
            vegetationRepository.getVegetationStreamByMonitorLogId(id)
                .map { SearchVegetationUiState(it) }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SearchVegetationUiState()
        )

    val searchVegetationUiState: StateFlow<SearchVegetationUiState> = _searchVegetationUiState

    fun setMonitorLogId(id: Long) {
        _monitorLogId.value = id
        //update searchTransectUiState by triggering a recomposition
        Log.d("SearchTransectViewModel", "setMonitorLogId: $id")
    }

    fun deleteVegetation(id: Long) {
        viewModelScope.launch {
            vegetationRepository.deleteVegetationById(id)
        }
    }
}