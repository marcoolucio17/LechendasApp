package com.example.lechendasapp.screens

import android.content.Context
import android.location.LocationManager
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.example.lechendasapp.utils.BottomNavBar
import com.example.lechendasapp.utils.RadioButtonWithText
import com.example.lechendasapp.utils.TopBar3
import com.example.lechendasapp.viewmodels.FormularioViewModel
import com.google.android.gms.location.*


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
    onCoverageClick: (Long) -> Unit,
    onVegetationClick: (Long) -> Unit,
    onTrapClick: (Long) -> Unit,
    viewModel: FormularioViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.formsUiState

    val context = LocalContext.current
    var coordinates by remember { mutableStateOf("No disponible") }

    // Agarrar automaticamente las coordenadas
    LaunchedEffect(Unit) {
        viewModel.fetchCoordinates(context) { lat, lon ->
            coordinates = "Lat: $lat\nLon: $lon"
        }
    }

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(32.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = modifier.fillMaxSize()
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
                        onClick = {
                            viewModel.updateUiState(
                                uiState.copy(
                                    climateType = weatherState.name,
                                    errors = uiState.errors - "climateType"
                                )
                            )
                        }
                    )


                }
            }
            if (uiState.errors.containsKey("climateType")) {
                Text(
                    text = uiState.errors["climateType"] ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

        }
        item {
            Text("Época:", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Epoca.entries.forEach { epoca ->
                    RadioButtonWithText(
                        text = epoca.name,
                        isSelected = uiState.seasons == epoca.name,
                        onClick = {
                            viewModel.updateUiState(
                                uiState.copy(
                                    seasons = epoca.name,
                                    errors = uiState.errors - "seasons"
                                )
                            )
                        }
                    )
                }
            }
            if (uiState.errors.containsKey("seasons")) {
                Text(
                    text = uiState.errors["seasons"] ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
        item {
            Text(
                "Coordenadas:",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = coordinates,
                    modifier = Modifier
                        .padding(8.dp)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
                Image(
                    painter = painterResource(id = R.drawable.baseline_location_on_24),
                    contentDescription = "Recargar GPS",
                    modifier = Modifier
                        .size(48.dp)
                        .clickable {
                            viewModel.fetchCoordinates(context) { lat, lon ->
                                coordinates = "Lat: $lat\nLon: $lon"
                            }
                            Toast.makeText(context, "¡Coordenadas actualizadas con éxito!", Toast.LENGTH_SHORT).show()
                        }
                        .padding(8.dp)
                )
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
                                    zone = zone.displayName,
                                    errors = uiState.errors - "zone"
                                )
                            )
                        }
                    )
                }
            }
            if (uiState.errors.containsKey("zone")) {
                Text(
                    text = uiState.errors["zone"] ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
        item {
            Text(
                "Tipo de Registro",
                fontWeight = FontWeight.Bold,
            )
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                TipoRegistro.entries.forEach { tipo ->
                    RadioButtonWithText(
                        text = tipo.displayName,
                        isSelected = uiState.logType == tipo.displayName,
                        onClick = {
                            viewModel.updateUiState(
                                uiState.copy(
                                    logType = tipo.displayName,
                                    errors = uiState.errors - "logType"
                                )
                            )
                        }
                    )
                }
            }
            if (uiState.errors.containsKey("logType")) {
                Text(
                    text = uiState.errors["logType"] ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
        item(key = "boton") {
            Button(
                onClick = {
                    if (viewModel.validateFields()) {
                        Toast.makeText(context, "Formulario enviado!", Toast.LENGTH_SHORT).show()
                        viewModel.addNewForm { newId ->
                            when (uiState.logType) {
                                TipoRegistro.TRANSECTOS.displayName -> onTransectClick(newId)
                                TipoRegistro.PUNTO_CONTEO.displayName -> onTransectClick(newId)
                                TipoRegistro.BUSQUEDA_LIBRE.displayName -> onTransectClick(newId)
                                TipoRegistro.VALIDACION_COBERTURA.displayName -> onCoverageClick(newId)
                                TipoRegistro.PARCELA_VEGETACION.displayName -> onVegetationClick(newId)
                                TipoRegistro.CAMARAS_TRAMPA.displayName -> onTrapClick(newId)
                                TipoRegistro.VARIABLES_CLIMATICAS.displayName -> onClimateClick(newId)
                            }
                        }
                    } else {
                        Toast.makeText(context, "Por favor, complete todos los campos obligatorios.", Toast.LENGTH_SHORT).show()
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
fun WeatherIcon(iconRes: Int, isSelected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = iconRes),
        contentDescription = null,
        modifier = modifier
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


fun isGpsEnabled(context: Context): Boolean {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
}
