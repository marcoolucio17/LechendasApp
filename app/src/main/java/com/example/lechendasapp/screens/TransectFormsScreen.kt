package com.example.lechendasapp.screens

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import android.os.Build
import android.widget.Toast
import com.example.lechendasapp.viewmodels.CameraViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lechendasapp.R
import com.example.lechendasapp.utils.BottomNavBar
import com.example.lechendasapp.utils.SimpleInputBox
import com.example.lechendasapp.utils.TopBar3
import com.example.lechendasapp.viewmodels.AnimalUiSate
import com.example.lechendasapp.viewmodels.AnimalViewModel

@Composable
fun TransectFormsScreen(
    currentRoute: String = "transects",
    onBack: () -> Unit,
    onMenuClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit,
    monitorLogId: Long,
    cameraViewModel: CameraViewModel = viewModel(), // Agregamos el ViewModel de la cámara
    viewModel: AnimalViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    viewModel.setMonitorLogId(monitorLogId)
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
        TransectFormsContent(
            onUpdateUiState = viewModel::updateUiState,
            onAddNewAnimal = viewModel::saveAnimal,
            animalUiState = viewModel.animalUiState.value,
            modifier = modifier.padding(innerPadding)
        )
    }
}

@Composable
fun TransectFormsContent(
    onUpdateUiState: (AnimalUiSate) -> Unit,
    onAddNewAnimal: () -> Unit,
    animalUiState: AnimalUiSate,
    modifier: Modifier = Modifier
) {
    val (selectedAnimal, setSelectedAnimal) = remember { mutableStateOf<AnimalTypes?>(null) }
    val (selectedObservation, setSelectedObservation) = remember {
        mutableStateOf<ObservationTypes?>(
            null
        )
    }
    val evidenceFiles = remember { mutableStateListOf<String>() }

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        item {
            SimpleInputBox(
                labelText = "Número de Transecto",
                value = animalUiState.transectName,
                onValueChange = { onUpdateUiState(animalUiState.copy(transectName = it)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )
        }

        item {
            Text(
                "Tipo de Animal",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    AnimalTypes.entries.toTypedArray().take(3).forEach { animal ->
                        AnimalOptions(
                            animal = animal,
                            isSelected = animal.displayName == animalUiState.animalType,
                            onSelect = {
                                onUpdateUiState(animalUiState.copy(animalType = animal.displayName))
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    AnimalTypes.entries.drop(3).forEach { animal ->
                        AnimalOptions(
                            animal = animal,
                            isSelected = animal.displayName == animalUiState.animalType,
                            onSelect = {
                                onUpdateUiState(animalUiState.copy(animalType = animal.displayName))
                            }
                        )
                    }
                }
            }
        }

        item {
            SimpleInputBox(
                labelText = "Nombre Común",
                value = animalUiState.commonName,
                onValueChange = { onUpdateUiState(animalUiState.copy(commonName = it)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )
        }

        item {
            SimpleInputBox(
                labelText = "Nombre Científico",
                value = animalUiState.scientificName,
                onValueChange = { onUpdateUiState(animalUiState.copy(scientificName = it)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            ) // TODO: Hacer opcional
        }

        item {
            SimpleInputBox(
                labelText = "Número de Individuos",
                value = animalUiState.quantity,
                onValueChange = { onUpdateUiState(animalUiState.copy(quantity = it)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )
        }

        item {
            Text("Tipo de Observación", fontWeight = FontWeight.Bold)
            Column {
                ObservationTypes.entries.forEach { observation ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = animalUiState.observationType == observation.displayName,
                            onClick = {  onUpdateUiState(animalUiState.copy(observationType = observation.displayName))  }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(observation.displayName)
                    }
                }
            }
        }

        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier
                    .width(450.dp)
                    .padding(
                        horizontal = dimensionResource(R.dimen.padding_medium),
                        vertical = dimensionResource(R.dimen.padding_small)
                    )
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    shape = RoundedCornerShape(topStart = 16.dp, bottomEnd = 32.dp),
                    modifier = Modifier
                        .height(dimensionResource(R.dimen.small_button_height))
                        .weight(1f)
                ) {
                    Text(
                        text = "Tomar foto",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                Button(
                    onClick = { /*TODO*/ },
                    shape = RoundedCornerShape(topStart = 32.dp, bottomEnd = 16.dp),
                    modifier = Modifier
                        .height(dimensionResource(R.dimen.small_button_height))
                        .weight(1f)
                ) {
                    Text(
                        text = "Cargar foto",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        }

        item {
            SimpleInputBox(
                labelText = "Observaciones",
                singleLine = false,
                modifier = Modifier.height(150.dp),
                value = animalUiState.observations.toString(),
                onValueChange = { onUpdateUiState(animalUiState.copy(observations = it)) },
            )
        }

        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = onAddNewAnimal,
                    modifier = Modifier.weight(1f),
                ) {
                    Text("Enviar", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun AnimalOptions(
    animal: AnimalTypes,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(80.dp)
    ) {
        Image(
            painter = painterResource(id = animal.iconRes),
            contentDescription = animal.name,
            modifier = Modifier
                .size(50.dp)
                .padding(4.dp)
                .background(
                    color = if (isSelected) Color(0xFFE0F7FA) else Color.Transparent,
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable { onSelect() }
        )
        Text(animal.displayName)
    }
}

@Composable
fun TransectsScreen(cameraViewModel: CameraViewModel = viewModel()) {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        // Mostrar la cámara
        CameraPreview(
            modifier = Modifier.fillMaxSize(),
            onUseCaseError = { error ->
                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            }
        )

        // Botón para tomar foto
        Button(
            onClick = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    cameraViewModel.takePhoto(
                        context = context,
                        saveToMediaStore = true,
                        onImageSaved = { file ->
                            Toast.makeText(context, "Foto guardada: ${file.path}", Toast.LENGTH_LONG).show()
                        },
                        onError = { exception ->
                            Toast.makeText(context, "Error: ${exception.message}", Toast.LENGTH_LONG).show()
                        }
                    )
                } else {
                    Toast.makeText(
                        context,
                        "La funcionalidad de la cámara requiere Android 9 (API 28) o superior",
                        Toast.LENGTH_LONG
                    ).show()
                }
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text("Tomar Foto")
        }
    }
}

enum class AnimalTypes(val displayName: String, val iconRes: Int) {
    MAMIFERO("Mamífero", R.drawable.capybara), // TODO: Cambiar icono placeholder
    AVE("Ave", R.drawable.capybara),
    REPTIL("Reptil", R.drawable.capybara),
    ANFIBIO("Anfibio", R.drawable.capybara),
    INSECTO("Insecto", R.drawable.capybara)
}

enum class ObservationTypes(val displayName: String) {
    VIO("La Vió"),
    HUELLA("Huella"),
    RASTRO("Rastro"),
    CACERIA("Cacería"),
    LE_DJERON("Le Dijeron")
}