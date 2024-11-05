package com.example.lechendasapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
fun TransectoFormScreen(
    currentRoute: String = "transecto",
    onBack: () -> Unit,
    onMenuClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
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
        TransectoFormContent(
            modifier = modifier.padding(innerPadding)
        )
    }
}

@Composable
fun TransectoFormContent(
    modifier: Modifier = Modifier
) {
    val (selectedAnimal, setSelectedAnimal) = remember { mutableStateOf<AnimalType?>(null) }
    val (selectedObservation, setSelectedObservation) = remember { mutableStateOf<ObservationType?>(ObservationType.VIO) }
    val evidenceFiles = remember { mutableStateListOf<String>() }

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        item {
            SimpleInputBox(labelText = "Número de Transecto")
        }

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
                        AnimalOption(animal, selectedAnimal, setSelectedAnimal)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    AnimalType.values().drop(3).forEach { animal ->
                        AnimalOption(animal, selectedAnimal, setSelectedAnimal)
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
                    onClick = { /* Acción para atrás */ }, //TODO
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(Color(0xFF4CAF50))
                ) {
                    Text("Atrás", color = Color.White)
                }
                Button(
                    onClick = { /* Acción para enviar */ }, //TODO: Implementar
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
fun AnimalOption(
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


enum class AnimalType(val displayName: String, val iconRes: Int) {
    MAMIFERO("Mamífero", R.drawable.capybara), // TODO: Cambiar icono placeholder
    AVE("Ave", R.drawable.capybara),
    REPTIL("Reptil", R.drawable.capybara),
    ANFIBIO("Anfibio", R.drawable.capybara),
    INSECTO("Insecto", R.drawable.capybara)
}

enum class ObservationType(val displayName: String) {
    VIO("La Vió"),
    HUELLA("Huella"),
    RASTRO("Rastro"),
    CACERIA("Cacería"),
    LE_DJERON("Le Dijeron")
}

@ScreenPreviews
@Composable
fun TransectoFormScreenPreview() {
    LechendasAppTheme {
        TransectoFormScreen(
            onBack = {},
            onMenuClick = {},
            onSearchClick = {},
            onSettingsClick = {}
        )
    }
}
