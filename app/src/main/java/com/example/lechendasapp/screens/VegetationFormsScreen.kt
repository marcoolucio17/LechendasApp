package com.example.lechendasapp.screens

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lechendasapp.R
import com.example.lechendasapp.data.model.Photo
import com.example.lechendasapp.preview.ScreenPreviews
import com.example.lechendasapp.ui.theme.LechendasAppTheme
import com.example.lechendasapp.utils.BottomNavBar
import com.example.lechendasapp.utils.PhotoGallery
import com.example.lechendasapp.utils.SimpleInputBox
import com.example.lechendasapp.utils.TopBar3
import com.example.lechendasapp.viewmodels.VegetationViewModel
import com.example.lechendasapp.viewmodels.VegetationUiState
import kotlinx.coroutines.launch

@Composable
fun VegetationFormsScreen(
    currentRoute: String = "trap",
    onBack: () -> Unit,
    onMenuClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onCameraClick: () -> Unit,
    monitorLogId: Long,
    id: Long? = null,
    viewModel: VegetationViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    viewModel.setMonitorLogId(monitorLogId)
    if (id != null) {
        viewModel.setVegetationId(id)
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
        VegetationFormsContent(
            vegetationUiState = viewModel.vegetationUiState.value,
            updateUiState = viewModel::updateUiState,
            onAddNewLog = viewModel::addNewLog,
            validateFields = viewModel::validateFields,
            onCameraClick = onCameraClick,
            onPickImage = viewModel::pickImage,
            onGetImage = viewModel::getImage,
            unassociatedPhotos = unassociatedPhotos,
            associatedPhotos = associatedPhotos,
            modifier = modifier.padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun VegetationFormsContent(
    vegetationUiState: VegetationUiState,
    updateUiState: (VegetationUiState) -> Unit,
    validateFields: () -> Boolean,
    onAddNewLog: () -> Unit,
    onCameraClick: () -> Unit,
    onPickImage: (Context, ActivityResultLauncher<Intent>) -> Unit,
    onGetImage: (String) -> Unit,
    unassociatedPhotos: List<Photo>,
    associatedPhotos: List<Photo>,
    modifier: Modifier = Modifier
) {
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
    val context = LocalContext.current
    val listState = rememberLazyListState() // Estado para controlar el scroll
    val coroutineScope = rememberCoroutineScope() // Para ejecutar scroll en un coroutine

    LazyColumn(
        state = listState,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(36.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        item {
            SimpleInputBox(
                labelText = "Código",
                value = vegetationUiState.code,
                onValueChange = {
                    updateUiState(vegetationUiState.copy(
                        code = it,
                        errors = vegetationUiState.errors - "code"
                    ))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                isError = vegetationUiState.errors.containsKey("code"),
                errorText = vegetationUiState.errors["code"]
            )
        }

        item {
            Column {
                Text(
                    text = "Cuadrante",
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .width(450.dp),
                    style = MaterialTheme.typography.titleMedium
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .width(450.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(24.dp),
                        modifier = Modifier.padding(top = 8.dp, end = 32.dp)
                    ) {
                        CuadranteMain.entries.forEach { cuadranteMain ->
                            Button(
                                onClick = {
                                    try {
                                        updateUiState(
                                            vegetationUiState.copy(
                                                quadrant = cuadranteMain.name,
                                                errors = vegetationUiState.errors.toMutableMap().apply { remove("quadrant") }
                                            )
                                        )
                                    } catch (e: Exception) {
                                        updateUiState(
                                            vegetationUiState.copy(
                                                errors = vegetationUiState.errors.toMutableMap().apply {
                                                    this["quadrant"] = "Error al seleccionar cuadrante: ${e.message}"
                                                }
                                            )
                                        )
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (vegetationUiState.quadrant == cuadranteMain.name)
                                        MaterialTheme.colorScheme.inversePrimary
                                    else
                                        MaterialTheme.colorScheme.surface,
                                    contentColor = if (vegetationUiState.quadrant == cuadranteMain.name)
                                        MaterialTheme.colorScheme.onPrimary
                                    else
                                        MaterialTheme.colorScheme.onSurface
                                ),
                                shape = MaterialTheme.shapes.small,
                                border = BorderStroke(
                                    1.dp,
                                    if (vegetationUiState.quadrant == cuadranteMain.name)
                                        MaterialTheme.colorScheme.primary
                                    else
                                        Color.Gray
                                ),
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(100.dp)
                            ) {
                                Text(
                                    text = cuadranteMain.name,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                    ) {
                        CuadranteSecond.entries.forEach { cuadranteSecond ->
                            Button(
                                onClick = {
                                    try {
                                        updateUiState(
                                            vegetationUiState.copy(
                                                quadrantSecond = cuadranteSecond.name,
                                                errors = vegetationUiState.errors.toMutableMap().apply { remove("quadrantSecond") }
                                            )
                                        )
                                    } catch (e: Exception) {
                                        updateUiState(
                                            vegetationUiState.copy(
                                                errors = vegetationUiState.errors.toMutableMap().apply {
                                                    this["quadrantSecond"] = "Error al seleccionar cuadrante secundario: ${e.message}"
                                                }
                                            )
                                        )
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (vegetationUiState.quadrantSecond == cuadranteSecond.name)
                                        MaterialTheme.colorScheme.inversePrimary
                                    else
                                        MaterialTheme.colorScheme.surface,
                                    contentColor = if (vegetationUiState.quadrantSecond == cuadranteSecond.name)
                                        MaterialTheme.colorScheme.onPrimary
                                    else
                                        MaterialTheme.colorScheme.onSurface
                                ),
                                shape = MaterialTheme.shapes.medium,
                                border = BorderStroke(
                                    1.dp,
                                    if (vegetationUiState.quadrantSecond == cuadranteSecond.name)
                                        MaterialTheme.colorScheme.primary
                                    else
                                        Color.Gray
                                ),
                            ) {
                                Text(
                                    text = cuadranteSecond.name,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }
                Text(
                    text = vegetationUiState.errors["quadrant"] ?: "",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = vegetationUiState.errors["quadrantSecond"] ?: "",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        item {
            Column {
                Text(
                    text = "Subcuadrante",
                    modifier = Modifier
                        .width(450.dp)
                        .padding(bottom = 16.dp),
                    style = MaterialTheme.typography.titleMedium
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    SubCuadrante.entries.forEach { subCuadrante ->
                        Button(
                            onClick = {
                                try {
                                    updateUiState(
                                        vegetationUiState.copy(
                                            subQuadrant = subCuadrante.displayName,
                                            errors = vegetationUiState.errors.toMutableMap().apply { remove("subQuadrant") }
                                        )
                                    )
                                } catch (e: Exception) {
                                    updateUiState(
                                        vegetationUiState.copy(
                                            errors = vegetationUiState.errors.toMutableMap().apply {
                                                this["subQuadrant"] = "Error al seleccionar subcuadrante: ${e.message}"
                                            }
                                        )
                                    )
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (vegetationUiState.subQuadrant == subCuadrante.displayName)
                                    MaterialTheme.colorScheme.inversePrimary
                                else
                                    MaterialTheme.colorScheme.surface,
                                contentColor = if (vegetationUiState.subQuadrant == subCuadrante.displayName)
                                    MaterialTheme.colorScheme.onPrimary
                                else
                                    MaterialTheme.colorScheme.onSurface
                            ),
                            shape = MaterialTheme.shapes.large,
                            border = BorderStroke(
                                1.dp,
                                if (vegetationUiState.subQuadrant == subCuadrante.displayName)
                                    MaterialTheme.colorScheme.primary
                                else
                                    Color.Gray
                            )
                        ) {
                            Text(
                                text = subCuadrante.displayName,
                                color = Color.Black
                            )
                        }
                    }
                }
                Text(
                    text = vegetationUiState.errors["subQuadrant"] ?: "",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        item {
            Column {
                Text(
                    text = "Hábito de crecimiento",
                    modifier = Modifier
                        .width(450.dp)
                        .padding(bottom = 16.dp),
                    style = MaterialTheme.typography.titleMedium
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Habito.entries.forEach { habito ->
                        Button(
                            onClick = {
                                try {
                                    updateUiState(
                                        vegetationUiState.copy(
                                            growthHabit = habito.name,
                                            errors = vegetationUiState.errors.toMutableMap().apply { remove("growthHabit") }
                                        )
                                    )
                                } catch (e: Exception) {
                                    updateUiState(
                                        vegetationUiState.copy(
                                            errors = vegetationUiState.errors.toMutableMap().apply {
                                                this["growthHabit"] = "Error al seleccionar hábito de crecimiento: ${e.message}"
                                            }
                                        )
                                    )
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (vegetationUiState.growthHabit == habito.name)
                                    MaterialTheme.colorScheme.inversePrimary
                                else
                                    MaterialTheme.colorScheme.surface,
                                contentColor = if (vegetationUiState.growthHabit == habito.name)
                                    MaterialTheme.colorScheme.onPrimary
                                else
                                    MaterialTheme.colorScheme.onSurface
                            ),
                            shape = MaterialTheme.shapes.large,
                            border = BorderStroke(
                                1.dp,
                                if (vegetationUiState.growthHabit == habito.name)
                                    MaterialTheme.colorScheme.primary
                                else
                                    Color.Gray
                            )
                        ) {
                            Column {
                                Text(
                                    text = habito.displayName,
                                    color = Color.Black
                                )
                                Text(
                                    text = habito.size,
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
                Text(
                    text = vegetationUiState.errors["growthHabit"] ?: "",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        item {
            SimpleInputBox(
                labelText = "Nombre Común",
                value = vegetationUiState.commonName,
                onValueChange = {
                    updateUiState(vegetationUiState.copy(
                        commonName = it,
                        errors = vegetationUiState.errors - "commonName"
                    ))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                isError = vegetationUiState.errors.containsKey("commonName"),
                errorText = vegetationUiState.errors["commonName"]
            )
        }
        item {
            SimpleInputBox(
                labelText = "Nombre Científico",
                value = vegetationUiState.scientificName.toString(),
                onValueChange = {
                    updateUiState(vegetationUiState.copy(
                        scientificName = it
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
                labelText = "Placa",
                value = vegetationUiState.plate,
                onValueChange = {
                    updateUiState(vegetationUiState.copy(
                        plate = it,
                        errors = vegetationUiState.errors - "plate"
                    ))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                isError = vegetationUiState.errors.containsKey("plate"),
                errorText = vegetationUiState.errors["plate"]
            )
        }

        item {
            SimpleInputBox(
                labelText = "Circunferencia (cm)",
                value = vegetationUiState.circumference,
                onValueChange = {
                    updateUiState(
                        vegetationUiState.copy(
                            circumference = it,
                            errors = vegetationUiState.errors - "circumference"
                        )
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                isError = vegetationUiState.errors.containsKey("circumference"),
                errorText = vegetationUiState.errors["circumference"]
            )
        }

        item {
            SimpleInputBox(
                labelText = "Distancia (mt)",
                value = vegetationUiState.distance,
                onValueChange = {
                    updateUiState(
                        vegetationUiState.copy(
                            distance = it,
                            errors = vegetationUiState.errors - "distance"
                        )
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                isError = vegetationUiState.errors.containsKey("distance"),
                errorText = vegetationUiState.errors["distance"]
            )
        }

        item {
            SimpleInputBox(
                labelText = "Altura (mt)",
                value = vegetationUiState.height,
                onValueChange = {
                    updateUiState(
                        vegetationUiState.copy(
                            height = it,
                            errors = vegetationUiState.errors - "height"
                        )
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                isError = vegetationUiState.errors.containsKey("height"),
                errorText = vegetationUiState.errors["height"]
            )
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
                value = vegetationUiState.observations.toString(),
                onValueChange = { updateUiState(vegetationUiState.copy(observations = it)) } ,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
            )
        }
        item {
                Button(
                    onClick = {
                        if (validateFields()) {
                            onAddNewLog()
                            //  // Resetea el formulario
                            Toast.makeText(context, "Formulario enviado!", Toast.LENGTH_SHORT).show()
                        }
                        coroutineScope.launch {
                            listState.scrollToItem(0) // Mueve al inicio
                        }
                    },
                    enabled = vegetationUiState.errors.isEmpty(),
                    modifier = Modifier
                        .height(dimensionResource(R.dimen.small_button_height))
                ) {
                    Text(
                        text = "Guardar",
                        style = MaterialTheme.typography.titleSmall
                    )
                }

        }

    }
}


enum class CuadranteMain {
    A,
    B,
}

enum class CuadranteSecond {
    C,
    D,
    E,
    F,
    G
}

enum class SubCuadrante(val displayName: String) {
    A1("1"),
    A2("2"),
    A3("3"),
    A4("4")
}

enum class Habito(val displayName: String, val size: String) {
    ARBUSTO("Arbusto", "< 1 mt"),
    ARBOLITO("Arbolito", "1 - 3 mt"),
    ARBOL("Arbol grande", "> 3 mt")
}

@ScreenPreviews
@Composable
fun VegetationFormsContent() {
    LechendasAppTheme {
        VegetationFormsScreen(
            onBack = {},
            onMenuClick = {},
            onSearchClick = {},
            onSettingsClick = {},
            onCameraClick = {},
            monitorLogId = 1
        )
    }
}