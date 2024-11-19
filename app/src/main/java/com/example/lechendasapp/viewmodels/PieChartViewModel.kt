package com.example.lechendasapp.viewmodels

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.lechendasapp.data.repository.AnimalRepository
import com.example.lechendasapp.data.repository.ClimateRepository
import com.example.lechendasapp.data.repository.CoverageRepository
import com.example.lechendasapp.data.repository.MonitorLogRepository
import com.example.lechendasapp.data.repository.PhotoRepository
import com.example.lechendasapp.data.repository.TrapRepository
import com.example.lechendasapp.data.repository.VegetationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class PieChartViewModel @Inject constructor(
    private val animalRepository: AnimalRepository,
    private val climateRepository: ClimateRepository,
    private val coverageRepository: CoverageRepository,
    private val photoRepository: PhotoRepository,
    private val trapRepository: TrapRepository,
    private val vegetationRepository: VegetationRepository,
    private val monitorLogRepository: MonitorLogRepository
) : ViewModel() {
    val _totalAmount = MutableStateFlow(0)
    val totalAmount: StateFlow<Int> = _totalAmount.asStateFlow()

    val _listForms = MutableStateFlow<List<Float>>(emptyList())
    val listForms: StateFlow<List<Float>> = _listForms.asStateFlow()

    init {
        getListForms()
        getTotalAmount()
    }

    fun getListForms() {
        _listForms.value = listOf()
        viewModelScope.launch{
            _listForms.value = listOf(
                animalRepository.countAnimal(),
                climateRepository.countClimate(),
                coverageRepository.countConverage(),
                photoRepository.countPhoto(),
                trapRepository.countTrap(),
                vegetationRepository.countVegetation()
            ) .map { it.toFloat() }
        }
    }

    fun getTotalAmount() {
        viewModelScope.launch {
            _totalAmount.value = monitorLogRepository.countMonitorLog()
        }
    }


}