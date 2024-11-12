package com.example.lechendasapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.lechendasapp.viewmodels.ClimateUiState
import com.example.lechendasapp.viewmodels.ClimateViewModel


@Composable
fun ClimateScreen(
    currentRoute: String = "climate",
    onBack: () -> Unit,
    onMenuClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit,
    monitorLogId: Long,
    viewModel: ClimateViewModel = hiltViewModel(),
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
        ClimateContent(
            climateUiState = viewModel.climateUiState.value,
            updateUiState = viewModel::updateUiState,
            addNewLog = viewModel::addNewLog,
            modifier = modifier.padding(innerPadding)
        )
    }
}

@Composable
fun ClimateContent(
    climateUiState: ClimateUiState,
    addNewLog: () -> Unit,
    updateUiState: (ClimateUiState) -> Unit,
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
                labelText = "Pluviosidad (mm)",
                value = climateUiState.rainfall.toString(),
                onValueChange = {
                    updateUiState(
                        climateUiState.copy(
                            rainfall = it
                        )
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
            )
        }
        item {
            SimpleInputBox(
                labelText = "Temperatura máxima",
                value = climateUiState.maxTemp.toString(),
                onValueChange = {
                    updateUiState(
                        climateUiState.copy(
                            maxTemp = it
                        )
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
            )
        }
        item {
            SimpleInputBox(
                labelText = "Temperatura mínima",
                value = climateUiState.minTemp.toString(),
                onValueChange = {
                    updateUiState(
                        climateUiState.copy(
                            minTemp = it
                        )
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
            )
        }
        item {
            SimpleInputBox(
                labelText = "Húmedad máxima",
                value = climateUiState.maxHumidity.toString(),
                onValueChange = {
                    updateUiState(
                        climateUiState.copy(
                            maxHumidity = it
                        )
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
            )
        }
        item {
            SimpleInputBox(
                labelText = "Húmedad mínima",
                value = climateUiState.minHumidity.toString(),
                onValueChange = {
                    updateUiState(
                        climateUiState.copy(
                            minHumidity = it
                        )
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
            )
        }
        item {
            SimpleInputBox(
                labelText = "Nivel de quebrada (mt)",
                value = climateUiState.ravineLevel.toString(),
                onValueChange = {
                    updateUiState(
                        climateUiState.copy(
                            ravineLevel = it
                        )
                    )
                },
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
                    .padding(horizontal = dimensionResource(R.dimen.padding_medium))
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
                onClick = { addNewLog() },
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
            monitorLogId = 1
        )
    }
}


