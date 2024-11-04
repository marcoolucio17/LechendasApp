package com.example.lechendasapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lechendasapp.R
import com.example.lechendasapp.preview.ScreenPreviews
import com.example.lechendasapp.ui.theme.LechendasAppTheme
import com.example.lechendasapp.utils.BottomNavBar
import com.example.lechendasapp.utils.SimpleInputBox
import com.example.lechendasapp.utils.TopBar3
import com.example.lechendasapp.viewmodels.ClimateViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


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
            viewModel = viewModel,
            modifier = modifier.padding(innerPadding)
        )
    }
}

@Composable
fun ClimateContent(
    viewModel: ClimateViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val climateUiState = viewModel.climateUiState

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
                onValueChange = { newValue ->
                    viewModel.updateUiState(
                        climateUiState.copy(
                            rainfall = newValue.toIntOrNull() ?: 0
                        )
                    )
                }
            )
        }
        item {
            SimpleInputBox(
                labelText = "Temperatura máxima",
                value = climateUiState.maxTemp.toString(),
                onValueChange = { newValue ->
                    viewModel.updateUiState(
                        climateUiState.copy(
                            maxTemp = newValue.toIntOrNull() ?: 0
                        )
                    )
                }
            )
        }
        item {
            SimpleInputBox(
                labelText = "Temperatura mínima",
                value = climateUiState.minTemp.toString(),
                onValueChange = { newValue ->
                    viewModel.updateUiState(
                        climateUiState.copy(
                            minTemp = newValue.toIntOrNull() ?: 0
                        )
                    )
                }
            )
        }
        item {
            SimpleInputBox(
                labelText = "Húmedad máxima",
                value = climateUiState.maxHumidity.toString(),
                onValueChange = { newValue ->
                    viewModel.updateUiState(
                        climateUiState.copy(
                            maxHumidity = newValue.toIntOrNull() ?: 0
                        )
                    )
                }
            )
        }
        item {
            SimpleInputBox(
                labelText = "Húmedad mínima",
                value = climateUiState.minHumidity.toString(),
                onValueChange = { newValue ->
                    viewModel.updateUiState(
                        climateUiState.copy(
                            minHumidity = newValue.toIntOrNull() ?: 0
                        )
                    )
                }
            )
        }
        item {
            SimpleInputBox(
                labelText = "Nivel de quebrada (mt)",
                value = climateUiState.ravineLevel.toString(),
                onValueChange = { newValue ->
                    viewModel.updateUiState(
                        climateUiState.copy(
                            ravineLevel = newValue.toIntOrNull() ?: 0
                        )
                    )
                }
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
                    viewModel.updateUiState(climateUiState.copy(observations = newValue))
                },
                singleLine = false,
                modifier = Modifier.height(150.dp)
            )
        }
        item {
            Button(
                onClick = { viewModel.addNewLog() },
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


