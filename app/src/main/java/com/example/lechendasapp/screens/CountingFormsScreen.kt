package com.example.lechendasapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.lechendasapp.R
import com.example.lechendasapp.preview.ScreenPreviews
import com.example.lechendasapp.ui.theme.LechendasAppTheme
import com.example.lechendasapp.utils.BottomNavBar
import com.example.lechendasapp.utils.SimpleInputBox
import com.example.lechendasapp.utils.TopBar3

@Composable
fun CountingFormsScreen(
    currentRoute: String = "counting",
    onBack: () -> Unit,
    onMenuClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = { TopBar3(onBack = onBack, title = "Formulario de Conteo") },
        bottomBar = {
            BottomNavBar(
                currentRoute = currentRoute,
                onHome = onMenuClick,
                onSearch = onSearchClick,
                onSettings = onSettingsClick
            )
        }
    ) { innerPadding ->
        CountingFormsContent(
            modifier = modifier.padding(innerPadding)
        )
    }
}

@Composable
fun CountingFormsContent(
    modifier: Modifier = Modifier
) {
    val (selectedAnimal, setSelectedAnimal) = remember { mutableStateOf<AnimalType?>(null) }
    val (selectedObservation, setSelectedObservation) = remember { mutableStateOf<ObservationType?>(ObservationType.VIO) }
    val (selectedObservationHeight, setSelectedObservationHeight) = remember { mutableStateOf<ObservationHeight?>(ObservationHeight.BAJA) }
    val evidenceFiles = remember { mutableStateListOf<String>() }

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        item {
            Text("Tipo de Animal", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    AnimalType.values().take(3).forEach { animal ->
                        AnimalOptionCounting(animal, selectedAnimal, setSelectedAnimal)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    AnimalType.values().drop(3).forEach { animal ->
                        AnimalOptionCounting(animal, selectedAnimal, setSelectedAnimal)
                    }
                }
            }
        }

        item {
            SimpleInputBox(labelText = "Nombre Común")
        }

        item {
            SimpleInputBox(labelText = "Nombre Científico") // TODO: Hacer opcional
        }

        item {
            SimpleInputBox(labelText = "Número de Individuos")
        }

        item {
            Text("Tipo de Observación", fontWeight = FontWeight.Bold)
            Column {
                ObservationType.values().forEach { observation ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = selectedObservation == observation,
                            onClick = { setSelectedObservation(observation) }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(observation.displayName)
                    }
                }
            }
        }

        // Nueva sección de Altura de Observación
        item {
            Text("Altura de Observación", fontWeight = FontWeight.Bold)
            Column {
                ObservationHeight.values().forEach { height ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = selectedObservationHeight == height,
                            onClick = { setSelectedObservationHeight(height) }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(height.displayName)
                    }
                }
            }
        }

        item {
            Text("Evidencias", fontWeight = FontWeight.Bold)
            Button(
                onClick = { /* lógica para elegir archivo */ },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF4CAF50))
            ) {
                Text("Elige archivo", color = Color.White)
            }
            Column {
                evidenceFiles.forEach { file ->
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(file)
                        Text("✕", color = Color.Red, modifier = Modifier.clickable {
                            evidenceFiles.remove(file)
                        })
                    }
                }
            }
        }

        item {
            SimpleInputBox(labelText = "Observaciones", singleLine = false, modifier = Modifier.height(150.dp))
        }

        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { /* Acción para atrás */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(Color(0xFF4CAF50))
                ) {
                    Text("Atrás", color = Color.White)
                }
                Button(
                    onClick = { /* Acción para enviar */ },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(Color(0xFF4CAF50))
                ) {
                    Text("Enviar", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun AnimalOptionCounting(
    animal: AnimalType,
    selectedAnimal: AnimalType?,
    setSelectedAnimal: (AnimalType) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(80.dp)
            .clickable { setSelectedAnimal(animal) }
    ) {
        Image(
            painter = painterResource(id = animal.iconRes),
            contentDescription = animal.name,
            modifier = Modifier
                .size(50.dp)
                .padding(4.dp)
                .background(
                    color = if (selectedAnimal == animal) Color(0xFFE0F7FA) else Color.Transparent,
                    shape = RoundedCornerShape(8.dp)
                )
        )
        Text(animal.displayName)
    }
}

// Nueva enumeración para la altura de observación
enum class ObservationHeight(val displayName: String) {
    BAJA("Baja <1mt"),
    MEDIA("Media 1-3mt"),
    ALTA("Alta >3mt")
}

@ScreenPreviews
@Composable
fun CountingFormScreenPreview() {
    LechendasAppTheme {
        CountingFormsScreen(
            onBack = {},
            onMenuClick = {},
            onSearchClick = {},
            onSettingsClick = {}
        )
    }
}
