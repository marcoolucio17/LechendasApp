package com.example.lechendasapp.views

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


@Composable
fun FormularyInitialScreen(
    onBack: () -> Unit,
    currentRoute: String = "formulary",
    onMenuClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Scaffold(
        topBar = { CustomBoxFormLayout() },
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
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun FormularioContent(modifier: Modifier = Modifier) {
    var nombre by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var localidad by remember { mutableStateOf("") }
    var selectedWeather by remember { mutableStateOf<WeatherState?>(null) }
    var selectedEpoca by remember { mutableStateOf<Epoca?>(null) }
    var selectedRegistro by remember { mutableStateOf<TipoRegistro?>(null) }

    val context = LocalContext.current

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFE6F0D3))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            CustomTextField(value = nombre, onValueChange = { nombre = it }, placeholder = "Nombre")
        }
        item {
            CustomTextField(value = fecha, onValueChange = { fecha = it }, placeholder = "Fecha")
        }
        item {
            CustomTextField(value = localidad, onValueChange = { localidad = it }, placeholder = "Localidad")
        }
        item {
            Text("Estado del Tiempo:", fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                WeatherIcon(iconRes = R.drawable.capybara, isSelected = selectedWeather == WeatherState.SUNNY) {
                    selectedWeather = WeatherState.SUNNY
                }
                WeatherIcon(iconRes = R.drawable.capybara, isSelected = selectedWeather == WeatherState.CLOUDY) {
                    selectedWeather = WeatherState.CLOUDY
                }
                WeatherIcon(iconRes = R.drawable.capybara, isSelected = selectedWeather == WeatherState.RAINY) {
                    selectedWeather = WeatherState.RAINY
                }
            }
        }
        item {
            Text("Época:", fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                RadioButtonWithText(text = "Verano/Seca", isSelected = selectedEpoca == Epoca.VERANO) {
                    selectedEpoca = Epoca.VERANO
                }
                RadioButtonWithText(text = "Invierno/Lluviosa", isSelected = selectedEpoca == Epoca.INVIERNO) {
                    selectedEpoca = Epoca.INVIERNO
                }
            }
        }
        item {
            Text("Tipo de Registro", fontWeight = FontWeight.Bold)
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                TipoRegistro.values().forEach { tipo ->
                    RadioButtonWithText(text = tipo.displayName, isSelected = selectedRegistro == tipo) {
                        selectedRegistro = tipo
                    }
                }
            }
        }
        item {
            Button(
                onClick = {
                    Toast.makeText(context, "Formulario enviado!", Toast.LENGTH_SHORT).show()
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
