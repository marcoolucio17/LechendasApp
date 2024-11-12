package com.example.lechendasapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lechendasapp.R
import com.example.lechendasapp.preview.ScreenPreviews
import com.example.lechendasapp.ui.theme.LechendasAppTheme
import com.example.lechendasapp.utils.BottomNavBar
import com.example.lechendasapp.utils.SimpleInputBox
import com.example.lechendasapp.utils.TopBar3
import com.example.lechendasapp.viewmodels.TrapUiState
import com.example.lechendasapp.viewmodels.TrapViewModel

@Composable
fun TrapFormsScreen(
    currentRoute: String = "trap",
    onBack: () -> Unit,
    onMenuClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit,
    viewModel: TrapViewModel = hiltViewModel(),
    monitorLogId: Long,
    id: Long? = null,
    modifier: Modifier = Modifier,
) {
    viewModel.setMonitorLogId(monitorLogId)
    if (id != null) {
        viewModel.setTrapId(id)
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
        TrapFormsContent(
            trapUiState = viewModel.trapUiState.value,
            updateUiState = viewModel::updateUiState,
            updateCheckList = viewModel::updateCheckList,
            onAddNewLog = viewModel::addNewLog,
            modifier = modifier.padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TrapFormsContent(
    trapUiState: TrapUiState,
    updateUiState: (TrapUiState) -> Unit,
    updateCheckList: (CheckList, Boolean) -> Unit,
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
                value = trapUiState.code,
                onValueChange = { updateUiState(trapUiState.copy(code = it)) } ,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
            )
        }
        item {
            SimpleInputBox(
                labelText = "Nombre cámara",
                value = trapUiState.cameraName,
                onValueChange = { updateUiState(trapUiState.copy(cameraName = it)) } ,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
            )
        }
        item {
            SimpleInputBox(
                labelText = "Placa cámara",
                value = trapUiState.cameraPlate,
                onValueChange = { updateUiState(trapUiState.copy(cameraPlate = it)) } ,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
            )
        }
        item {
            SimpleInputBox(
                labelText = "Placa Guaya",
                value = trapUiState.guayaPlate,
                onValueChange = { updateUiState(trapUiState.copy(guayaPlate = it)) } ,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
            )
        }
        item {
            SimpleInputBox(
                labelText = "Ancho camino (mt)",
                value = trapUiState.roadWidth,
                onValueChange = { updateUiState(trapUiState.copy(roadWidth = it)) } ,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
            )
        }
        item {
            SimpleInputBox(
                labelText = "Fecha de instalación",
                value = trapUiState.installationDate,
                onValueChange = { updateUiState(trapUiState.copy(installationDate = it)) } ,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
            )
        }
        item {
            SimpleInputBox(
                labelText = "Distancia de objetivo (mt)",
                value = trapUiState.objectiveDistance,
                onValueChange = { updateUiState(trapUiState.copy(objectiveDistance = it)) } ,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),

            )
        }
        item {
            SimpleInputBox(
                labelText = "Altura del lente (mt)",
                value = trapUiState.lensHeight,
                onValueChange = { updateUiState(trapUiState.copy(lensHeight = it)) } ,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
            )
        }
        item {
            FlowRow (
                modifier = Modifier
                    .width(450.dp)
            ) {
                Text(
                    "Lista de chequeo",
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = dimensionResource(R.dimen.padding_medium),
                            vertical = dimensionResource(R.dimen.padding_small)
                        )
                )
                CheckList.entries.forEach { check ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .width(220.dp)
                    ) {
                        Checkbox(
                            checked = trapUiState.checkList[check] == true,
                            onCheckedChange = { isChecked ->
                                updateCheckList(check, isChecked)
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = check.displayName)
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
                value = trapUiState.observations.toString(),
                onValueChange = { updateUiState(trapUiState.copy(observations = it)) } ,
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

enum class CheckList(val displayName: String) {
    PROGRAMMED("Programada"),
    MEMORY("Memoria"),
    CRAWLTEST("Prueba de gateo"),
    INSTALLED("Instalada"),
    CAMERASIGN("Letrero de cámara"),
    ON("Prendida")
}


@ScreenPreviews
@Composable
fun TrapFormsScreenPreview() {
    LechendasAppTheme {
        TrapFormsScreen(
            onBack = {},
            onMenuClick = {},
            onSearchClick = {},
            onSettingsClick = {},
            monitorLogId = 0
        )
    }
}