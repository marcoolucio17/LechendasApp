package com.example.lechendasapp.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lechendasapp.R
import com.example.lechendasapp.data.model.MonitorLog
import com.example.lechendasapp.data.repository.MonitorLogRepository
import com.example.lechendasapp.preview.ScreenPreviews
import com.example.lechendasapp.ui.theme.LechendasAppTheme
import com.example.lechendasapp.utils.BottomNavBar
import com.example.lechendasapp.utils.RadioButtonWithText
import com.example.lechendasapp.utils.TopBar3
import com.example.lechendasapp.viewmodels.FormsUiState
import com.example.lechendasapp.viewmodels.FormularioViewModel
import kotlinx.coroutines.flow.Flow


@Composable
fun FormularyInitialScreen(
    onBack: () -> Unit,
    currentRoute: String = "formulary",
    onMenuClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit,

    //all next forms
    //onConteoClick: () -> Unit,
    //onFreeClick: () -> Unit,
    onTransectClick: (Long) -> Unit,
    onVegetationClick: (Long) -> Unit,
    onClimateClick: (Long) -> Unit,
    onCoverageClick: (Long) -> Unit,
    onTrapClick: (Long) -> Unit,

    viewModel: FormularioViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = { TopBar3(onBack = onBack, title = "Formulario") },
        bottomBar = {
            BottomNavBar(
                currentRoute = currentRoute,
                onHome = onMenuClick,
                onSearch = onSearchClick,
                onSettings = onSettingsClick
            )
        }
    ) { innerPadding ->
        FormularioContent(
            onClimateClick = onClimateClick,
            onTransectClick = onTransectClick,
            //onConteoClick = onConteoClick,
            //onFreeClick = onFreeClick,
            onCoverageClick = onCoverageClick,
            onVegetationClick = onVegetationClick,
            onTrapClick = onTrapClick,
            viewModel = viewModel,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FormularioContent(
    onClimateClick: (Long) -> Unit,
    onTransectClick: (Long) -> Unit,
    //onConteoClick: () -> Unit,
    //onFreeClick: () -> Unit,
    onCoverageClick: (Long) -> Unit,
    onVegetationClick: (Long) -> Unit,
    onTrapClick: (Long) -> Unit,
    viewModel: FormularioViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.formsUiState

    val monitorLogId by viewModel.monitorLogId

    val context = LocalContext.current

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = modifier
            .fillMaxSize()
    ) {
        item {
            Text(
                "Estado del Tiempo:",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                WeatherState.entries.forEach { weatherState ->
                    val icon = when (weatherState) {
                        WeatherState.SUNNY -> R.drawable.sol
                        WeatherState.CLOUDY -> R.drawable.nublado
                        WeatherState.RAINY -> R.drawable.lluvia
                    }
                    WeatherIcon(
                        iconRes = icon,
                        isSelected = uiState.climateType == weatherState.name,
                        onClick = { viewModel.updateUiState(uiState.copy(climateType = weatherState.name)) }
                    )


                }
            }

        }
        item {
            Text("Época:", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Epoca.entries.forEach { epoca ->
                    RadioButtonWithText(
                        text = epoca.name,
                        isSelected = uiState.seasons == epoca.name,
                        onClick = { viewModel.updateUiState(uiState.copy(seasons = epoca.name)) }
                    )
                }
            }
        }
        item {
            Text("Zona:", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Zone.entries.forEach { zone ->
                    RadioButtonWithText(
                        text = zone.displayName,
                        isSelected = uiState.zone == zone.displayName,
                        onClick = {
                            viewModel.updateUiState(
                                uiState.copy(
                                    zone = zone.displayName
                                )
                            )
                        }
                    )
                }
            }
        }
        item {
            Text(
                "Tipo de Registro",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                TipoRegistro.entries.forEach { tipo ->
                    RadioButtonWithText(
                        text = tipo.displayName,
                        isSelected = uiState.logType == tipo.displayName,
                        onClick = { viewModel.updateUiState(uiState.copy(logType = tipo.displayName)) }
                    )
                }
            }
        }
        item(key = "boton") {
            Button(
                onClick = {
                    Toast.makeText(context, "Formulario enviado!", Toast.LENGTH_SHORT).show()

                    viewModel.addNewForm { newId ->
                        when (uiState.logType) {
                            TipoRegistro.TRANSECTOS.displayName -> onTransectClick(newId)
                            TipoRegistro.PUNTO_CONTEO.displayName ->  onTransectClick(newId)
                            TipoRegistro.BUSQUEDA_LIBRE.displayName ->  onTransectClick(newId)
                            TipoRegistro.VALIDACION_COBERTURA.displayName -> onCoverageClick(newId)
                            TipoRegistro.PARCELA_VEGETACION.displayName -> onVegetationClick(newId)
                            TipoRegistro.CAMARAS_TRAMPA.displayName -> onTrapClick(newId)
                            TipoRegistro.VARIABLES_CLIMATICAS.displayName -> onClimateClick(newId)
                        }
                    }
                },
                modifier = Modifier
                    .width(dimensionResource(R.dimen.button_width))
                    .height(dimensionResource(R.dimen.button_height))
            ) {
                Text("SIGUIENTE")
            }
        }
    }
}

@Composable
fun WeatherIcon(iconRes: Int, isSelected: Boolean, onClick: () -> Unit) {
    Image(
        painter = painterResource(id = iconRes),
        contentDescription = null,
        modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(if (isSelected) Color.Gray else Color.LightGray)
            .padding(8.dp)
            .clickable { onClick() },
        contentScale = ContentScale.Crop
    )
}

// Enums para los diferentes tipos de opciones
@Immutable
enum class WeatherState { SUNNY, CLOUDY, RAINY }

@Immutable
enum class Epoca { VERANO, INVIERNO }

@Immutable
enum class TipoRegistro(val displayName: String) {
    TRANSECTOS("Fauna en Transectos"),
    PUNTO_CONTEO("Fauna en Punto de Conteo"),
    BUSQUEDA_LIBRE("Fauna Búsqueda Libre"),
    VALIDACION_COBERTURA("Validación de Cobertura"),
    PARCELA_VEGETACION("Parcela de Vegetación"),
    CAMARAS_TRAMPA("Cámaras Trampa"),
    VARIABLES_CLIMATICAS("Variables Climáticas")
}

@Immutable
enum class Zone(val displayName: String) {
    A("Bosque"),
    B("Arreglo Agroforestal"),
    C("Cultivos Transitorios"),
    D("Cultivos Permanentes")
}

@ScreenPreviews
@Composable
fun FormularyInitialScreenPreview() {
    LechendasAppTheme {
        FormularyInitialScreen(
            onBack = {},
            currentRoute = "formulary",
            onMenuClick = {},
            onSearchClick = {},
            onSettingsClick = {},
            onClimateClick = {},
            onCoverageClick = {},
            onTrapClick = {},
            onVegetationClick = {},
            onTransectClick = {},
            //onConteoClick = {},
            //onFreeClick = {},
            viewModel = rememberPreviewFormularioViewModel()
        )
    }
}

class MockMonitorLogRepository2 : MonitorLogRepository {
    override fun getMonitorLogsStream(): Flow<List<MonitorLog>> {
        TODO("Not yet implemented")
    }

    override fun getMonitorLogByIdStream(monitorLogId: Long): Flow<MonitorLog> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllMonitorLogs(): List<MonitorLog> {
        TODO("Not yet implemented")
    }

    override suspend fun getMonitorLogById(monitorLogId: Long): MonitorLog? {
        TODO("Not yet implemented")
    }

    override suspend fun addMonitorLog(monitorLog: MonitorLog) : Long {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMonitorLog(monitorLog: MonitorLog) {
        TODO("Not yet implemented")
    }
}

@Composable
fun rememberPreviewFormularioViewModel(): FormularioViewModel {
    val mockMonitorLogRepository = MockMonitorLogRepository2()

    return remember {
        FormularioViewModel(mockMonitorLogRepository).apply {
            // Initialize with sample data for the preview
            updateUiState(
                FormsUiState(
                    climateType = "SUNNY",
                    seasons = "VERANO",
                    logType = "Fauna en Transectos",
                    zone = "Bosque"
                )
            )
        }
    }
}