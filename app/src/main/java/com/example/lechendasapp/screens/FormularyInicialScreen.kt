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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.lechendasapp.R
import com.example.lechendasapp.utils.BottomNavBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lechendasapp.utils.TopBar3
import com.example.lechendasapp.viewmodels.FormularioViewModel
import androidx.compose.ui.tooling.preview.Preview
import com.example.lechendasapp.ui.theme.LechendasAppTheme


@Composable
fun FormularyInitialScreen(
    onBack: () -> Unit,
    currentRoute: String = "formulary",
    onMenuClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit,
//    viewModel: FormularioViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = { TopBar3(onBack = onBack, title="Formulario") },
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
//            viewModel = viewModel,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun FormularioContent(
    viewModel: FormularioViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.formsUiState.value // Collect UI state as a State

    val context = LocalContext.current

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFE6F0D3))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            CustomTextField(
                value =  uiState.form.observations.orEmpty(),
                onValueChange = { viewModel.updateUiState(uiState.form.copy(observations = it)) },
                placeholder = "Observación"
            )
        }
        item {
            CustomTextField(
                value = uiState.form.id.toString(),
                onValueChange = { date -> viewModel.updateUiState(uiState.form.copy(id = date.toLongOrNull() ?: 200L)) },
                placeholder = "0"
            )
        }
        item {
            CustomTextField(
                value = uiState.form.gpsCoordinates,
                onValueChange = { viewModel.updateUiState(uiState.form.copy(gpsCoordinates = it)) },
                placeholder = "Localidad"
            )
        }
        item {
            Text("Estado del Tiempo:", fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                WeatherState.values().forEach { weatherState ->
                    WeatherIcon(
                        iconRes = R.drawable.capybara,
                        isSelected = uiState.form.climateType == weatherState.name,
                        onClick = { viewModel.updateUiState(uiState.form.copy(climateType = weatherState.name)) }
                    )
                }
            }
        }
        item {
            Text("Época:", fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Epoca.values().forEach { epoca ->
                    RadioButtonWithText(
                        text = epoca.name,
                        isSelected = uiState.form.seasons == epoca.name,
                        onClick = { viewModel.updateUiState(uiState.form.copy(seasons = epoca.name)) }
                    )
                }
            }
        }
        item {
            Text("Tipo de Registro", fontWeight = FontWeight.Bold)
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                TipoRegistro.values().forEach { tipo ->
                    RadioButtonWithText(
                        text = tipo.displayName,
                        isSelected = uiState.form.logType == tipo.displayName,
                        onClick = { viewModel.updateUiState(uiState.form.copy(logType = tipo.displayName)) }
                    )
                }
            }
        }
        item {
            Button(
                onClick = {
                    Toast.makeText(context, "Formulario enviado!", Toast.LENGTH_SHORT).show()
                    viewModel.addNewForm() // Save form to the database
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("SIGUIENTE")
            }
        }
    }
}


@Composable
fun CustomBoxFormLayout(
    modifier: Modifier = Modifier, ) {
    Box(modifier = modifier.height(80.dp)) {
        Image(
            painter = painterResource(id = R.drawable.semicircle),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.capybara),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.profilepicture),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            contentAlignment = Alignment.Center
        ) {
            Text("Formulario Inicial", style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold))
        }

        Box(
            modifier = Modifier
                .size(50.dp)
                .offset(y = 20.dp)
                .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
                .align(Alignment.BottomCenter),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.AddCircle,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

// Componentes personalizados para el formulario
@Composable
fun CustomTextField(value: String, onValueChange: (String) -> Unit, placeholder: String) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = Color.Gray) },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = MaterialTheme.shapes.small)
            .padding(8.dp)
    )
}

@Composable
fun WeatherIcon(iconRes: Int, isSelected: Boolean, onClick: () -> Unit) {
    Image(
        painter = painterResource(id = iconRes),
        contentDescription = null,
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(if (isSelected) Color.Gray else Color.LightGray)
            .padding(8.dp)
            .clickable { onClick() },
        contentScale = ContentScale.Crop
    )
}

@Composable
fun RadioButtonWithText(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { onClick() }
    ) {
        RadioButton(selected = isSelected, onClick = onClick)
        Text(text = text)
    }
}

// Enums para los diferentes tipos de opciones
enum class WeatherState { SUNNY, CLOUDY, RAINY }
enum class Epoca { VERANO, INVIERNO }
enum class TipoRegistro(val displayName: String) {
    TRANSECTOS("Fauna en Transectos"),
    PUNTO_CONTEO("Fauna en Punto de Conteo"),
    BUSQUEDA_LIBRE("Fauna Búsqueda Libre"),
    VALIDACION_COBERTURA("Validación de Cobertura"),
    PARCELA_VEGETACION("Parcela de Vegetación"),
    CAMARAS_TRAMPA("Cámaras Trampa"),
    VARIABLES_CLIMATICAS("Variables Climáticas")
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FormularyInitialScreenPreview() {
    LechendasAppTheme {
        FormularyInitialScreen(
            onBack = {},
            currentRoute = "formulary",
            onMenuClick = {},
            onSearchClick = {},
            onSettingsClick = {}
        )
    }
}