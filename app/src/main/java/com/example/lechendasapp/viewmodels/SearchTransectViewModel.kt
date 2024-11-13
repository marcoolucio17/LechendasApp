package com.example.lechendasapp.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lechendasapp.data.model.Animal
import com.example.lechendasapp.data.model.MonitorLog
import com.example.lechendasapp.data.repository.AnimalRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import androidx.compose.runtime.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

data class SearchTransectUiState(
    val animal: List<Animal> = listOf()
)

@HiltViewModel
class SearchTransectViewModel @Inject constructor(
    private val animalRepository: AnimalRepository
) : ViewModel() {
    private val _monitorLogId = MutableStateFlow(-1L)
    val monitorLogId: StateFlow<Long> = _monitorLogId

    @OptIn(ExperimentalCoroutinesApi::class)
    private val _searchTransectUiState: StateFlow<SearchTransectUiState> =
        _monitorLogId.flatMapLatest { id ->
            animalRepository.getAnimalsStreamByMonitorLogId(id)
                .map { SearchTransectUiState(it) }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SearchTransectUiState()
        )

    val searchTransectUiState: StateFlow<SearchTransectUiState> = _searchTransectUiState

    fun setMonitorLogId(id: Long) {
        _monitorLogId.value = id
        //update searchTransectUiState by triggering a recomposition
        Log.d("SearchTransectViewModel", "setMonitorLogId: $id")
    }

    fun deleteTransect(id: Long) {
        viewModelScope.launch {
            animalRepository.deleteAnimalById(id)
        }
    }
}