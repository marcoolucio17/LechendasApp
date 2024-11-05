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
)

fun Vegetation.toVegetationUiState(): VegetationUiState = VegetationUiState(
    code = this.code,
    quadrant = this.quadrant, //TODO: separate quadrants
    subQuadrant = this.subQuadrant,
    growthHabit = this.growthHabit,
    commonName = this.commonName,
    scientificName = this.scientificName,
    plate = this.plate,
    circumference = this.circumference.toString(),
    distance = this.distance.toString(),
    height = this.height.toString(),
    observations = this.observations,
)

fun VegetationUiState.toVegetation(): Vegetation = Vegetation(
    id = 0, // This can be default / handled by DAO

    monitorLogId = 0, // this is handled with the route

    code = this.code,
    quadrant = this.quadrant + this.quadrantSecond,
    subQuadrant = this.subQuadrant,
    growthHabit = this.growthHabit,
    commonName = this.commonName,
    scientificName = this.scientificName,
    plate = this.plate,
    circumference = this.circumference.toInt(),
    distance = this.distance.toInt(),
    height = this.height.toInt(),
    observations = this.observations,
)


@HiltViewModel
class VegetationViewModel @Inject constructor(
    private val vegetationRepository: VegetationRepository
) : ViewModel() {
    private val _vegetationUiState = mutableStateOf(VegetationUiState())
    val vegetationUiState: State<VegetationUiState> = _vegetationUiState

    private val _monitorLogId = mutableLongStateOf(0L)
    val monitorLogId: State<Long> = _monitorLogId

    fun updateUiState(newUi: VegetationUiState) {
        _vegetationUiState.value = newUi
    }

    fun updateSelectedCuadranteMain(selectedIndex: Int) {
        _vegetationUiState.value = _vegetationUiState.value.copy(quadrant = CuadranteMain.entries[selectedIndex].name)
    }

    fun updateSelectedCuadranteSecond(selectedIndex: Int) {
        Log.d("SELECTED INDEX", selectedIndex.toString())
        _vegetationUiState.value = _vegetationUiState.value.copy(quadrantSecond = CuadranteSecond.entries[selectedIndex].name)
        Log.d("SELECTED QUADRANT", _vegetationUiState.value.quadrantSecond)
    }

    fun updateSelectedSubCuadrante(selectedIndex: Int) {
        Log.d("SELECTED INDEX", selectedIndex.toString())
        _vegetationUiState.value = _vegetationUiState.value.copy(subQuadrant = SubCuadrante.entries[selectedIndex].displayName)
        Log.d("SELECTED QUADRANT", _vegetationUiState.value.subQuadrant)
    }

    fun updateSelectedHabito(selectedIndex: Int) {
        Log.d("SELECTED INDEX", selectedIndex.toString())
        _vegetationUiState.value = _vegetationUiState.value.copy(growthHabit = Habito.entries[selectedIndex].name)
        Log.d("SELECTED QUADRANT", _vegetationUiState.value.growthHabit)
    }

    fun setMonitorLogId(id: Long) {
        _monitorLogId.longValue = id
    }

    fun addNewLog() {
        viewModelScope.launch {
            val newLog = _vegetationUiState.value.toVegetation()

            //update id values
            val new = newLog.copy(monitorLogId = _monitorLogId.longValue)

            // TODO: VALIDATE NOT BLANK FIELDS

            vegetationRepository.insertVegetation(new)
        }
    }
}