package com.example.lechendasapp.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import kotlinx.coroutines.launch

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
            validateFields = viewModel::validateFields,
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
    validateFields: () -> Boolean,
    onAddNewLog: () -> Unit,
    modifier: Modifier = Modifier
) {

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
                value = trapUiState.code,
                onValueChange = {
                    updateUiState(trapUiState.copy(
                        code = it,
                        errors = trapUiState.errors - "code"
                    ))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                isError = trapUiState.errors.containsKey("code"),
                errorText = trapUiState.errors["code"]
            )
        }
        item {
            SimpleInputBox(
                labelText = "Nombre cámara",
                value = trapUiState.cameraName,
                onValueChange = {
                    updateUiState(trapUiState.copy(
                        cameraName = it,
                        errors = trapUiState.errors - "cameraName"
                    ))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                isError = trapUiState.errors.containsKey("cameraName"),
                errorText = trapUiState.errors["cameraName"]
            )
        }
        item {
            SimpleInputBox(
                labelText = "Placa cámara",
                value = trapUiState.cameraPlate,
                onValueChange = {
                    updateUiState(trapUiState.copy(
                        cameraPlate = it,
                        errors = trapUiState.errors - "cameraPlate"
                    ))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                isError = trapUiState.errors.containsKey("cameraPlate"),
                errorText = trapUiState.errors["cameraPlate"]
            )
        }

        item {
            SimpleInputBox(
                labelText = "Placa Guaya",
                value = trapUiState.guayaPlate,
                onValueChange = {
                    updateUiState(trapUiState.copy(
                        guayaPlate = it,
                        errors = trapUiState.errors - "guayaPlate"
                    ))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                isError = trapUiState.errors.containsKey("guayaPlate"),
                errorText = trapUiState.errors["guayaPlate"]
            )
        }

        item {
            SimpleInputBox(
                labelText = "Ancho camino (mt)",
                value = trapUiState.roadWidth,
                onValueChange = {
                    updateUiState(trapUiState.copy(
                        roadWidth = it,
                        errors = trapUiState.errors - "roadWidth"
                    ))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                isError = trapUiState.errors.containsKey("roadWidth"),
                errorText = trapUiState.errors["roadWidth"]
            )
        }

        item {
            SimpleInputBox(
                labelText = "Fecha de instalación",
                value = trapUiState.installationDate,
                onValueChange = {
                    updateUiState(trapUiState.copy(
                        installationDate = it,
                        errors = trapUiState.errors - "installationDate"
                    ))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                isError = trapUiState.errors.containsKey("installationDate"),
                errorText = trapUiState.errors["installationDate"]
            )
        }

        item {
            SimpleInputBox(
                labelText = "Distancia de objetivo (mt)",
                value = trapUiState.objectiveDistance,
                onValueChange = {
                    updateUiState(trapUiState.copy(
                        objectiveDistance = it,
                        errors = trapUiState.errors - "objectiveDistance"
                    ))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                isError = trapUiState.errors.containsKey("objectiveDistance"),
                errorText = trapUiState.errors["objectiveDistance"]
            )
        }
        item {
            SimpleInputBox(
                labelText = "Altura del lente (mt)",
                value = trapUiState.lensHeight,
                onValueChange = {
                    updateUiState(trapUiState.copy(
                        lensHeight = it,
                        errors = trapUiState.errors - "lensHeight"
                    ))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                isError = trapUiState.errors.containsKey("lensHeight"),
                errorText = trapUiState.errors["lensHeight"]
            )
        }
        item {
            Column(
                modifier = Modifier.width(450.dp)
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

                // Mostrar errores si existen
                if (trapUiState.errors.containsKey("checkList")) {
                    Text(
                        text = trapUiState.errors["checkList"] ?: "",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(
                            horizontal = dimensionResource(R.dimen.padding_medium),
                            vertical = dimensionResource(R.dimen.padding_small)
                        )
                    )
                }

                FlowRow(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Itera a través de todas las opciones de la lista de chequeo
                    CheckList.entries.forEach { check ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.width(220.dp)
                        ) {
                            Checkbox(
                                checked = trapUiState.checkList[check] == true,  // Se marca si es true
                                onCheckedChange = { isChecked ->
                                    // Actualiza el estado del checkList
                                    updateCheckList(check, isChecked)

                                    // Si se selecciona al menos una opción, elimina el error de checkList
                                    if (trapUiState.checkList.any { it.value }) {
                                        updateUiState(
                                            trapUiState.copy(errors = trapUiState.errors - "checkList")
                                        )
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = check.displayName)
                        }
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CheckListSection(
    trapUiState: TrapUiState,
    updateCheckList: (CheckList, Boolean) -> Unit,
    updateUiState: (TrapUiState) -> Unit
) {
    Column(
        modifier = Modifier.width(450.dp)
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

        // Mostrar errores si existen
        if (trapUiState.errors.containsKey("checkList")) {
            Text(
                text = trapUiState.errors["checkList"] ?: "",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(R.dimen.padding_medium),
                    vertical = dimensionResource(R.dimen.padding_small)
                )
            )
        }

        FlowRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            CheckList.entries.forEach { check ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .width(220.dp)
                        .clickable {
                            // Toggle the checkbox when clicking anywhere in the row
                            val newValue = !(trapUiState.checkList[check] ?: false)
                            updateCheckList(check, newValue)

                            // Eliminar el error si al menos una opción está seleccionada
                            if (trapUiState.checkList.any { it.value }) {
                                updateUiState(
                                    trapUiState.copy(errors = trapUiState.errors - "checkList")
                                )
                            }
                        }
                ) {
                    Checkbox(
                        checked = trapUiState.checkList[check] ?: false,
                        onCheckedChange = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = check.displayName)
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