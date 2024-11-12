package com.example.lechendasapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lechendasapp.data.model.Climate
import com.example.lechendasapp.data.repository.ClimateRepository
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

data class SearchClimateUiState(
    val climate: List<Climate> = listOf()
)

@HiltViewModel
class SearchClimateViewModel @Inject constructor(
    private val climateRepository: ClimateRepository
) : ViewModel() {
    private val _monitorLogId = MutableStateFlow(-1L)
    val monitorLogId: StateFlow<Long> = _monitorLogId

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _searchClimateUiState: StateFlow<SearchClimateUiState> =
        _monitorLogId.flatMapLatest { id ->
            climateRepository.getClimateStreamByMonitorLogId(id)
                .map { SearchClimateUiState(it) }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SearchClimateUiState()
        )

    val searchClimateUiState: StateFlow<SearchClimateUiState> = _searchClimateUiState

    fun setMonitorLogId(id: Long) {
        _monitorLogId.value = id
        //update searchTransectUiState by triggering a recomposition
        Log.d("SearchTransectViewModel", "setMonitorLogId: $id")
    }

    fun deleteClimate(id: Long) {
        viewModelScope.launch {
            climateRepository.deleteClimateById(id)
        }
    }

}