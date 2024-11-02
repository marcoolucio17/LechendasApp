package com.example.lechendasapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lechendasapp.data.model.MonitorLog
import com.example.lechendasapp.data.repository.MonitorLogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


data class SearchUiState(
    val log: List<MonitorLog> = listOf()
)


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val monitorLogRepository: MonitorLogRepository
) : ViewModel() {
    val _searchUiState: StateFlow<SearchUiState> =
        monitorLogRepository.getMonitorLogsStream()
            .map { SearchUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = SearchUiState()
            )
}