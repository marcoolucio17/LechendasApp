package com.example.lechendasapp.screens

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
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
    onCameraClick: () -> Unit,
    viewModel: TrapViewModel = hiltViewModel(),
    monitorLogId: Long,
    id: Long? = null,
    modifier: Modifier = Modifier,
) {
    viewModel.setMonitorLogId(monitorLogId)
    if (id != null) {
        viewModel.setTrapId(id)
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
        TrapFormsContent(
            trapUiState = viewModel.trapUiState.value,
            updateUiState = viewModel::updateUiState,
            updateCheckList = viewModel::updateCheckList,
            validateFields = viewModel::validateFields,
            onAddNewLog = viewModel::addNewLog,
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
fun TrapFormsContent(
    trapUiState: TrapUiState,
    updateUiState: (TrapUiState) -> Unit,
    updateCheckList: (CheckList, Boolean) -> Unit,
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
            FlowRow(
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
                CheckList.entries.forEach { check ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.width(220.dp)
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

                // Mostrar error solo si no se ha seleccionado un checkbox
                if (trapUiState.errors.containsKey("checkList")) {
                    Text(
                        text = trapUiState.errors["checkList"] ?: "",
                        color = Color.Red, // Aquí puedes personalizar el color del error
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                    )
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
            onCameraClick = {},
            monitorLogId = 0
        )
    }
}