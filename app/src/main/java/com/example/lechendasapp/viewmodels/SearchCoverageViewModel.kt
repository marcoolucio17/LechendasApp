package com.example.lechendasapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lechendasapp.data.model.Coverage
import com.example.lechendasapp.data.repository.CoverageRepository
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

data class SearchCoverageUiState(
    val coverage: List<Coverage> = listOf()
)

@HiltViewModel
class SearchCoverageViewModel @Inject constructor(
    private val coverageRepository: CoverageRepository
) : ViewModel() {
    private val _monitorLogId = MutableStateFlow(-1L)
    val monitorLogId: StateFlow<Long> = _monitorLogId

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _searchCoverageUiState: StateFlow<SearchCoverageUiState> =
        _monitorLogId.flatMapLatest { id ->
            coverageRepository.getConverageStreamByMonitorLogId(id)
                .map { SearchCoverageUiState(it) }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SearchCoverageUiState()
        )

    val searchCoverageUiState: StateFlow<SearchCoverageUiState> = _searchCoverageUiState

    fun setMonitorLogId(id: Long) {
        _monitorLogId.value = id
        //update searchTransectUiState by triggering a recomposition
        // Log.d("SearchTransectViewModel", "setMonitorLogId: $id")
    }

    fun deleteCoverage(id: Long) {
        viewModelScope.launch {
            coverageRepository.deleteConverageById(id)
        }
    }

}