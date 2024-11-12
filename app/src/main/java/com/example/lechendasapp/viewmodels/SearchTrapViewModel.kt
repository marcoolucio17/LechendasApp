package com.example.lechendasapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lechendasapp.data.model.Trap
import com.example.lechendasapp.data.repository.TrapRepository
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

data class SearchTrapUiState(
    val trap: List<Trap> = listOf()
)

@HiltViewModel
class SearchTrapViewModel @Inject constructor(
    private val trapRepository: TrapRepository
) : ViewModel() {
    private val _monitorLogId = MutableStateFlow(-1L)
    val monitorLogId: StateFlow<Long> = _monitorLogId

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _searchTrapUiState: StateFlow<SearchTrapUiState> =
        _monitorLogId.flatMapLatest { id ->
            trapRepository.getTrapStreamByMonitorLogId(id)
                .map { SearchTrapUiState(it) }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SearchTrapUiState()
        )

    val searchTrapUiState: StateFlow<SearchTrapUiState> = _searchTrapUiState

    fun setMonitorLogId(id: Long) {
        _monitorLogId.value = id
        //update searchTransectUiState by triggering a recomposition
        Log.d("SearchTransectViewModel", "setMonitorLogId: $id")
    }

    fun deleteTrap(id: Long) {
        viewModelScope.launch {
            trapRepository.deleteTrapById(id)
        }
    }

}