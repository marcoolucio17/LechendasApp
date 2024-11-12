package com.example.lechendasapp.screens

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lechendasapp.R
import com.example.lechendasapp.preview.ScreenPreviews
import com.example.lechendasapp.ui.theme.LechendasAppTheme
import com.example.lechendasapp.utils.BottomNavBar
import com.example.lechendasapp.utils.SimpleInputBox
import com.example.lechendasapp.utils.TopBar3
import com.example.lechendasapp.viewmodels.VegetationViewModel
import com.example.lechendasapp.viewmodels.VegetationUiState

@Composable
fun VegetationFormsScreen(
    currentRoute: String = "trap",
    onBack: () -> Unit,
    onMenuClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit,
    monitorLogId: Long,
    id: Long? = null,
    viewModel: VegetationViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    viewModel.setMonitorLogId(monitorLogId)
    if (id != null) {
        viewModel.setVegetationId(id)
    }
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
            updateCuadranteMain = viewModel::updateSelectedCuadranteMain,
            updateCuadranteSecond = viewModel::updateSelectedCuadranteSecond,
            updateSubCuadrante = viewModel::updateSelectedSubCuadrante,
            updateHabito = viewModel::updateSelectedHabito,
            modifier = modifier.padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun VegetationFormsContent(
    vegetationUiState: VegetationUiState,
    updateUiState: (VegetationUiState) -> Unit,
    updateCuadranteMain: (Int) -> Unit,
    updateCuadranteSecond: (Int) -> Unit,
    updateSubCuadrante: (Int) -> Unit,
    updateHabito: (Int) -> Unit,
    onAddNewLog: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
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
                onValueChange = { updateUiState(vegetationUiState.copy(code = it)) } ,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
            )
        }
        item {
            Text(
                text = "Cuadrante",
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .width(450.dp)
            )
            Row (
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .width(450.dp)
            ) {
                Column (
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    modifier = Modifier.padding(top = 8.dp, end = 32.dp)
                ) {
                    CuadranteMain.entries.forEach {
                        Button(
                            onClick = { updateCuadranteMain(it.ordinal)  },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (vegetationUiState.quadrant == it.name) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.surface,
                                contentColor = if (vegetationUiState.quadrant == it.name) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                            ),
                            shape = MaterialTheme.shapes.small,
                            border = BorderStroke(1.dp, if (vegetationUiState.quadrant == it.name) MaterialTheme.colorScheme.primary else Color.Gray),
                            modifier = Modifier
                                .width(100.dp)
                                .height(100.dp)
                        ) {
                            Text(
                                text = it.name,
                                color = Color.Black
                            )
                        }
                    }
                }
                Column (
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    CuadranteSecond.entries.forEach {
                        Button(
                            onClick = { updateCuadranteSecond(it.ordinal) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor =  if (vegetationUiState.quadrantSecond == it.name) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.surface,
                                contentColor = if (vegetationUiState.quadrantSecond == it.name) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                            ),
                            shape = MaterialTheme.shapes.medium,
                            border = BorderStroke(1.dp, if (vegetationUiState.quadrantSecond == it.name) MaterialTheme.colorScheme.primary else Color.Gray),
                        ) {
                            Text(
                                text = it.name,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
        item {
            Text(
                text = "Subcuadrante",
                modifier = Modifier
                    .width(450.dp)
                    .padding(bottom = 16.dp)
            )
            Row (
                horizontalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                SubCuadrante.entries.forEach {
                    Button(
                        onClick = { updateSubCuadrante(it.ordinal) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor =  if ( vegetationUiState.subQuadrant == it.displayName ) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.surface,
                            contentColor =  if ( vegetationUiState.subQuadrant == it.displayName ) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                        ),
                        shape = MaterialTheme.shapes.large,
                        border = BorderStroke(1.dp,  if ( vegetationUiState.subQuadrant == it.displayName ) MaterialTheme.colorScheme.primary else Color.Gray,),
                    ) {
                        Text(
                            text = it.displayName,
                            color = Color.Black
                        )
                    }
                }
            }
        }
        item {
            Text(
                text = "Hábito de crecimiento",
                modifier = Modifier
                    .width(450.dp)
                    .padding(bottom = 16.dp)
            )
            Row (
                horizontalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                Habito.entries.forEach {
                    Button(
                        onClick = { updateHabito(it.ordinal) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor =  if ( vegetationUiState.growthHabit == it.name ) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.surface,
                            contentColor =  if ( vegetationUiState.growthHabit == it.name ) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                        ),
                        shape = MaterialTheme.shapes.large,
                        border =  BorderStroke(1.dp,  if ( vegetationUiState.subQuadrant == it.name ) MaterialTheme.colorScheme.primary else Color.Gray,),
                    ) {
                        Column (
                        ) {
                            Text(
                                text = it.displayName,
                                color = Color.Black
                            )
                            Text(
                                text = it.size,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
        item {
            SimpleInputBox(
                labelText = "Nombre común",
                value = vegetationUiState.commonName,
                onValueChange = { updateUiState(vegetationUiState.copy(commonName = it)) } ,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
            )
        }
        item {
            SimpleInputBox(
                labelText = "Nombre científico",
                value = vegetationUiState.scientificName.toString(),
                onValueChange = { updateUiState(vegetationUiState.copy(scientificName = it)) } ,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
            )
        }

        item {
            SimpleInputBox(
                labelText = "Placa",
                value = vegetationUiState.plate,
                onValueChange = { updateUiState(vegetationUiState.copy(plate = it)) } ,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
            )
        }

        item {
            SimpleInputBox(
                labelText = "Circunferencia (cm)",
                value = vegetationUiState.circumference,
                onValueChange = { updateUiState(vegetationUiState.copy(circumference = it)) } ,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
            )
        }
        item {
            SimpleInputBox(
                labelText = "Distancia (mt)",
                value = vegetationUiState.distance,
                onValueChange = { updateUiState(vegetationUiState.copy(distance = it)) } ,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
            )
        }
        item {
            SimpleInputBox(
                labelText = "Altura (mt)",
                value = vegetationUiState.height,
                onValueChange = { updateUiState(vegetationUiState.copy(height = it)) } ,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
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
                value = vegetationUiState.observations.toString(),
                onValueChange = { updateUiState(vegetationUiState.copy(observations = it)) } ,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
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
                    onClick = onAddNewLog,
                    modifier = Modifier

                        .height(dimensionResource(R.dimen.small_button_height))
                        .weight(1f)
                ) {
                    Text(
                        text = "Guardar",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
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
            monitorLogId = 1
        )
    }
}