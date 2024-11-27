package com.example.lechendasapp.viewmodels

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lechendasapp.data.model.MonitorLog
import com.example.lechendasapp.data.repository.AuthTokenRepository
import com.example.lechendasapp.data.repository.MonitorLogRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FormsUiState(
    val climateType: String = "",
    val seasons: String = "",
    val zone: String = "",
    val logType: String = "",
    val gpsCoordinates: String = "",
    val errors: Map<String, String> = emptyMap()
)


fun MonitorLog.toFormsUiState(): FormsUiState = FormsUiState(
    climateType = this.climateType,
    seasons = this.seasons,
    zone = this.zone,
    logType = this.logType,
    gpsCoordinates = this.gpsCoordinates
)

fun FormsUiState.toMonitorLog(): MonitorLog = MonitorLog(
    id = 0, // es automatico

    userId = "u_", // lo obtiene a la hora de mandarlo
    dateMillis = System.currentTimeMillis(),
    gpsCoordinates = this.gpsCoordinates,
    location = "l_",

    climateType = this.climateType,
    seasons = this.seasons,
    zone = this.zone,
    logType = this.logType,
)

@HiltViewModel
class FormularioViewModel @Inject constructor(
    private val monitorLogRepository: MonitorLogRepository,
    private val authTokenRepository: AuthTokenRepository
) : ViewModel() {
    private val _formsUiState = mutableStateOf(FormsUiState())
    val formsUiState: State<FormsUiState> = _formsUiState

    private val _monitorLogId = mutableLongStateOf(0L)
    val monitorLogId: State<Long> = _monitorLogId

    fun updateUiState(newUi: FormsUiState) {
        _formsUiState.value = newUi
    }

    fun addNewForm(onFormAdded: (Long) -> Unit) {
        viewModelScope.launch {
            val userIdToken = authTokenRepository.getIdToken()
            val newForm = _formsUiState.value.toMonitorLog().copy(userId = userIdToken ?: "-1")

            Log.d("FORMULARIO", "addNewForm: $newForm")

            val newId = monitorLogRepository.addMonitorLog(newForm)
            _monitorLogId.longValue = newId

            onFormAdded(newId)
        }
    }

    fun validateFields(): Boolean {
        val currentState = _formsUiState.value
        val errors = currentState.errors.toMutableMap()

        if (currentState.climateType.isBlank()) {
            errors["climateType"] = "El estado del tiempo es obligatorio."
        } else {
            errors.remove("climateType")
        }

        if (currentState.seasons.isBlank()) {
            errors["seasons"] = "La época es obligatoria."
        } else {
            errors.remove("seasons")
        }

        if (currentState.zone.isBlank()) {
            errors["zone"] = "La zona es obligatoria."
        } else {
            errors.remove("zone")
        }

        if (currentState.logType.isBlank()) {
            errors["logType"] = "El tipo de registro es obligatorio."
        } else {
            errors.remove("logType")
        }

        _formsUiState.value = currentState.copy(errors = errors)
        return errors.isEmpty()
    }



    fun fetchCoordinates(context: Context, onCoordinatesFetched: (Double, Double) -> Unit) {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.e("Location", "Permiso de ubicación no concedido.")
            return
        }

        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    onCoordinatesFetched(location.latitude, location.longitude)
                    _formsUiState.value = _formsUiState.value.copy(
                        gpsCoordinates = "${location.latitude}, ${location.longitude}"
                    )
                } else {
                    Log.e("Location", "No se pudo obtener la ubicación. Probando actualizaciones...")
                    // Solicitar actualizaciones de ubicación si no hay datos
                    requestLocationUpdates(context, fusedLocationProviderClient, onCoordinatesFetched)
                }
            }
            .addOnFailureListener { exception ->
                Log.e("Location", "Error al obtener la ubicación: ${exception.message}")
            }
    }

    fun requestLocationUpdates(
        context: Context,
        fusedLocationProviderClient: FusedLocationProviderClient,
        onCoordinatesFetched: (Double, Double) -> Unit
    ) {
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            10000 // Interval in milliseconds (10 seconds)
        ).apply {
            setMinUpdateIntervalMillis(5000) // Fastest interval in milliseconds (5 seconds)
        }.build()

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.e("Location", "Permiso de ubicación no concedido.")
            return
        }

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    fusedLocationProviderClient.removeLocationUpdates(this) // Detener actualizaciones
                    val location = locationResult.lastLocation
                    if (location != null) {
                        onCoordinatesFetched(location.latitude, location.longitude)
                    } else {
                        Log.e("Location", "No se pudo obtener la ubicación.")
                    }
                }
            },
            context.mainLooper
        )
    }

}