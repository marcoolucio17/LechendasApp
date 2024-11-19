package com.example.lechendasapp.screens

import android.widget.Toast
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.lechendasapp.viewmodels.ClimateUiState
import com.example.lechendasapp.viewmodels.ClimateViewModel
import kotlinx.coroutines.launch


@Composable
fun ClimateScreen(
    currentRoute: String = "climate",
    onBack: () -> Unit,
    onMenuClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onCameraClick: () -> Unit,
    monitorLogId: Long,
    id: Long? = null,
    viewModel: ClimateViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    viewModel.setMonitorLogId(monitorLogId)
    if (id != null) {
        viewModel.setClimateId(id)
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
        ClimateContent(
            climateUiState = viewModel.climateUiState.value,
            updateUiState = viewModel::updateUiState,
            addNewLog = viewModel::addNewLog,
            onCameraClick = onCameraClick,
            onPickImage = viewModel::pickImage,
            onGetImage = viewModel::getImage,
            unassociatedPhotos = unassociatedPhotos,
            associatedPhotos = associatedPhotos,
            validateFields = viewModel::validateFields,
            modifier = modifier.padding(innerPadding)
        )
    }
}

@Composable
fun ClimateContent(
    climateUiState: ClimateUiState,
    validateFields: () -> Boolean,
    addNewLog: () -> Unit,
    onCameraClick: () -> Unit,
    onPickImage: (Context, ActivityResultLauncher<Intent>) -> Unit,
    onGetImage: (String) -> Unit,
    unassociatedPhotos: List<Photo>,
    associatedPhotos: List<Photo>,
    updateUiState: (ClimateUiState) -> Unit,
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
        state = listState, // Asocia el estado de scroll
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(36.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        item {
            SimpleInputBox(
                labelText = "Pluviosidad (mm)",
                value = climateUiState.rainfall,
                onValueChange = {
                    updateUiState(
                        climateUiState.copy(
                            rainfall = it,
                            errors = climateUiState.errors - "rainfall"
                        )
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                isError = climateUiState.errors.containsKey("rainfall"),
                errorText = climateUiState.errors["rainfall"]
            )

        }
        item {
            SimpleInputBox(
                labelText = "Temperatura máxima",
                value = climateUiState.maxTemp,
                onValueChange = {
                    updateUiState(
                        climateUiState.copy(
                            maxTemp = it,
                            errors = climateUiState.errors - "maxTemp" // Limpia el error al cambiar el valor
                        )
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                isError = climateUiState.errors.containsKey("maxTemp"),
                errorText = climateUiState.errors["maxTemp"]
            )
        }
        item {
            SimpleInputBox(
                labelText = "Temperatura mínima",
                value = climateUiState.minTemp,
                onValueChange = {
                    updateUiState(
                        climateUiState.copy(
                            minTemp = it,
                            errors = climateUiState.errors - "minTemp" // Limpia el error al cambiar el valor
                        )
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                isError = climateUiState.errors.containsKey("minTemp"),
                errorText = climateUiState.errors["minTemp"]
            )
        }
        item {
            SimpleInputBox(
                labelText = "Húmedad máxima",
                value = climateUiState.maxHumidity,
                onValueChange = {
                    updateUiState(
                        climateUiState.copy(
                            maxHumidity = it,
                            errors = climateUiState.errors - "maxHumidity" // Limpia el error al cambiar el valor
                        )
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                isError = climateUiState.errors.containsKey("maxHumidity"),
                errorText = climateUiState.errors["maxHumidity"]
            )
        }
        item {
            SimpleInputBox(
                labelText = "Húmedad mínima",
                value = climateUiState.minHumidity,
                onValueChange = {
                    updateUiState(
                        climateUiState.copy(
                            minHumidity = it,
                            errors = climateUiState.errors - "minHumidity" // Limpia el error al cambiar el valor
                        )
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                isError = climateUiState.errors.containsKey("minHumidity"),
                errorText = climateUiState.errors["minHumidity"]
            )
        }
        item {
            SimpleInputBox(
                labelText = "Nivel de quebrada (mt)",
                value = climateUiState.ravineLevel,
                onValueChange = {
                    updateUiState(
                        climateUiState.copy(
                            ravineLevel = it,
                            errors = climateUiState.errors - "ravineLevel" // Limpia el error al cambiar el valor
                        )
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                isError = climateUiState.errors.containsKey("ravineLevel"),
                errorText = climateUiState.errors["ravineLevel"]
            )
        }
        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier
                    .width(450.dp)
                    .padding(horizontal = dimensionResource(R.dimen.padding_medium))
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
                value = climateUiState.observations,
                onValueChange = { newValue ->
                    updateUiState(climateUiState.copy(observations = newValue))
                },
                singleLine = false,
                modifier = Modifier.height(150.dp)
            )
        }
        item {
            Button(
                onClick = {
                    if (validateFields()) {
                        addNewLog()
                        //  // Resetea el formulario
                        Toast.makeText(context, "Formulario enviado!", Toast.LENGTH_SHORT).show()
                        coroutineScope.launch {
                            listState.scrollToItem(0) // Mueve al inicio
                        }
                    }
                },
                enabled = climateUiState.errors.isEmpty(),
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

//TODO: add mock repository for previews to work

@ScreenPreviews
@Composable
fun ClimateScreenPreview() {
    LechendasAppTheme {
        ClimateScreen(
            onBack = {},
            onMenuClick = {},
            onSearchClick = {},
            onSettingsClick = {},
            onCameraClick = {},
            monitorLogId = 1
        )
    }
}


