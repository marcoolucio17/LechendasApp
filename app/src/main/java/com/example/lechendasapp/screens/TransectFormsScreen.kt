package com.example.lechendasapp.screens

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lechendasapp.R
import com.example.lechendasapp.data.model.Photo
import com.example.lechendasapp.utils.BottomNavBar
import com.example.lechendasapp.utils.PhotoGallery
import com.example.lechendasapp.utils.SimpleInputBox
import com.example.lechendasapp.utils.TopBar3
import com.example.lechendasapp.viewmodels.AnimalUiSate
import com.example.lechendasapp.viewmodels.AnimalViewModel
import kotlinx.coroutines.launch

@Composable
fun TransectFormsScreen(
    currentRoute: String = "transects",
    onBack: () -> Unit,
    onMenuClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onCameraClick: () -> Unit,
    monitorLogId: Long,
    id: Long? = null,
    viewModel: AnimalViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    viewModel.setMonitorLogId(monitorLogId)
    if (id != null) {
        viewModel.setAnimalId(id)
    }
    LaunchedEffect(monitorLogId, id) {
        viewModel.fetchAssociatedPhotosIfNeeded()
    }

    val unassociatedPhotos by viewModel.unassociatedPhotos.collectAsState()
    val associatedPhotos by viewModel.associatedPhotos.collectAsState()

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
            saveAnimal = viewModel::saveAnimal,
            onAddNewAnimal = viewModel::saveAnimal,
            validateFields = viewModel::validateFields,
            animalUiState = viewModel.animalUiState.value,
            onCameraClick = onCameraClick,
            onPickImage = viewModel::pickImage,
            onGetImage = viewModel::getImage,
            unassociatedPhotos = unassociatedPhotos,
            associatedPhotos = associatedPhotos,
            modifier = modifier.padding(innerPadding)
        )
    }
}

@Composable
fun TransectFormsContent(
    onUpdateUiState: (AnimalUiSate) -> Unit,
    saveAnimal: () -> Unit,
    onAddNewAnimal: () -> Unit,
    validateFields: () -> Boolean,
    animalUiState: AnimalUiSate,
    onCameraClick: () -> Unit,
    onPickImage: (Context, ActivityResultLauncher<Intent>) -> Unit,
    onGetImage: (String) -> Unit,
    unassociatedPhotos: List<Photo>,
    associatedPhotos: List<Photo>,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                val imagePath = uri.toString()
                onGetImage(imagePath)
            }
        }
    }

    LazyColumn(
        state = listState, // Asocia el estado de scroll
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        item {
            SimpleInputBox(
                labelText = "Nombre de Transecto",
                value = animalUiState.transectName,
                onValueChange = { onUpdateUiState(animalUiState.copy(
                    transectName = it,
                    errors = animalUiState.errors - "transectName"
                    ))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                isError = animalUiState.errors.containsKey("transectName"),
                errorText = animalUiState.errors["transectName"]
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
                                onUpdateUiState(
                                    animalUiState.copy(
                                        animalType = animal.displayName, // Actualizamos el campo correcto
                                        errors = animalUiState.errors - "animalType" // Limpiamos el error asociado
                                    )
                                )
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
                                onUpdateUiState(
                                    animalUiState.copy(
                                        animalType = animal.displayName, // Actualizamos el campo correcto
                                        errors = animalUiState.errors - "animalType" // Limpiamos el error asociado
                                    )
                                )
                            }
                        )
                    }
                }
            }

            // Mostrar mensaje de error si existe
            if (animalUiState.errors.containsKey("animalType")) {
                Text(
                    text = animalUiState.errors["animalType"] ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }



        item {
            SimpleInputBox(
                labelText = "Nombre Común",
                value = animalUiState.commonName,
                onValueChange = { onUpdateUiState(animalUiState.copy(
                    commonName = it,
                    errors = animalUiState.errors - "commonName"
                ))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                isError = animalUiState.errors.containsKey("commonName"),
                errorText = animalUiState.errors["commonName"]
            )
        }

        item {
            SimpleInputBox(
                labelText = "Nombre Científico",
                value = animalUiState.scientificName,
                onValueChange = { onUpdateUiState(animalUiState.copy(
                    scientificName = it,
                    errors = animalUiState.errors - "scientificName"
                ))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )
        }

        item {
            SimpleInputBox(
                labelText = "Número de Individuos",
                value = animalUiState.quantity,
                onValueChange = { onUpdateUiState(animalUiState.copy(
                    quantity = it,
                    errors = animalUiState.errors - "quantity"
                ))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                isError = animalUiState.errors.containsKey("quantity"),
                errorText = animalUiState.errors["quantity"]
            )
        }

        item {
            Text(
                "Tipo de Observación",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                ObservationTypes.entries.forEach { observation ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        RadioButton(
                            selected = animalUiState.observationType == observation.displayName,
                            onClick = {
                                onUpdateUiState(
                                    animalUiState.copy(
                                        observationType = observation.displayName, // Actualizamos el campo correcto
                                        errors = animalUiState.errors - "observationType" // Limpiamos el error asociado
                                    )
                                )
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = observation.displayName,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            // Mostrar mensaje de error si existe
            if (animalUiState.errors.containsKey("observationType")) {
                Text(
                    text = animalUiState.errors["observationType"] ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                )
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
                    onClick = { onCameraClick() },
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
                    onClick = { onPickImage(context, imagePickerLauncher) },
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
            PhotoGallery(
                unassociatedPhotos = unassociatedPhotos,
                associatedPhotos = associatedPhotos
            )
        }

        item {
            SimpleInputBox(
                labelText = "Observaciones",
                singleLine = false,
                modifier = Modifier.height(150.dp),
                value = animalUiState.observations.toString(),
                onValueChange = { onUpdateUiState(animalUiState.copy(
                    observations = it,
                    errors = animalUiState.errors - "observations"
                ))
                },
            )
        }

        item {
                Button(
                    onClick = {
                        if (validateFields()) {
                            saveAnimal()
                            //  Resetea el formulario
                            Toast.makeText(context, "Formulario enviado!", Toast.LENGTH_SHORT).show()

                        }
                        coroutineScope.launch {
                            listState.scrollToItem(0) // Mueve al inicio
                        }
                    },
                    enabled = animalUiState.errors.isEmpty(),
                    modifier = Modifier
                ) {
                    Text(
                        text = "Guardar",
                        style = MaterialTheme.typography.titleSmall
                    )
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

enum class AnimalTypes(val displayName: String, val iconRes: Int) {
    MAMIFERO("Mamífero", R.drawable.panda), // TODO: Cambiar icono placeholder
    AVE("Ave", R.drawable.aguila),
    REPTIL("Reptil", R.drawable.cocodrilo),
    ANFIBIO("Anfibio", R.drawable.rana),
    INSECTO("Insecto", R.drawable.abeja)
}

enum class ObservationTypes(val displayName: String) {
    VIO("La Vió"),
    HUELLA("Huella"),
    RASTRO("Rastro"),
    CACERIA("Cacería"),
    LE_DJERON("Le Dijeron")
}