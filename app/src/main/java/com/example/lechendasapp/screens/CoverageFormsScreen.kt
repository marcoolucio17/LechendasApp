package com.example.lechendasapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.lechendasapp.utils.RadioButtonWithText
import com.example.lechendasapp.utils.SimpleInputBox
import com.example.lechendasapp.utils.TopBar3
import com.example.lechendasapp.viewmodels.CoverageViewModel
import com.example.lechendasapp.viewmodels.CoverageUiState

@Composable
fun CoverageFormsScreen(
    currentRoute: String = "trap",
    onBack: () -> Unit,
    onMenuClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit,
    viewModel : CoverageViewModel = hiltViewModel(),
    monitorLogId: Long,
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
        CoverageFormsContent(
            updateUiState = viewModel::updateUiState,
            saveCoverage = viewModel::saveCoverage,
            coverageUiState = viewModel.coverageUiState.value,
            updateTrackingOption = viewModel::updateTrackingOption,
            updateChangeOption = viewModel::updateChangeOption,
            updateCoverageOption = viewModel::updateCoverageOption,
            updateDisturbanceOption = viewModel::updateDisturbanceOption,
            modifier = modifier.padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CoverageFormsContent(
    updateUiState: (CoverageUiState) -> Unit,
    saveCoverage: () -> Unit,
    coverageUiState: CoverageUiState,
    updateTrackingOption: (SINO) -> Unit,
    updateChangeOption: (SINO) -> Unit,
    updateCoverageOption: (CoverageOptions) -> Unit,
    updateDisturbanceOption: (DisturbanceOptions) -> Unit,
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
                value = coverageUiState.code,
                onValueChange = { updateUiState(coverageUiState.copy(code = it)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )
        }
        item {
            Column(
                modifier = Modifier.width(450.dp)
            ) {
                // First Column (Seguimiento)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_medium))
                ) {
                    Text(
                        "Seguimiento",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .padding(vertical = dimensionResource(R.dimen.padding_small))
                            .weight(1f)
                    )
                    SINO.entries.forEach { option ->
                        RadioButtonWithText(
                            text = option.displayName,
                            isSelected = coverageUiState.tracking[option] == true,
                            onClick = {
                                updateTrackingOption(option)
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                // Second Column (Cambio)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = dimensionResource(R.dimen.padding_medium))
                ) {
                    Text(
                        "Cambio",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .padding(vertical = dimensionResource(R.dimen.padding_small))
                            .weight(1f)
                    )
                    SINO.entries.forEach { option ->
                        RadioButtonWithText(
                            text = option.displayName,
                            isSelected = coverageUiState.change[option] == true ,
                            onClick = {
                                 updateChangeOption(option)
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

        }
        item {
            Text("Cobertura", fontWeight = FontWeight.Bold)
            FlowRow(horizontalArrangement = Arrangement.Center,
                modifier = Modifier.width(450.dp)
            ) {
                CoverageOptions.entries.forEach { coverageOption ->
                    RadioButtonWithText(
                        text = coverageOption.name,
                        isSelected = coverageUiState.coverage[coverageOption] == true,
                        onClick = {
                            updateCoverageOption(coverageOption)
                        }
                    )
                }
            }
        }
        item {
            SimpleInputBox(
                labelText = "Tipo de cultivo",
                value = coverageUiState.cropType,
                onValueChange = { updateUiState(coverageUiState.copy(cropType = it)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
            )
        }
        item {
            Text("Disturbio", fontWeight = FontWeight.Bold)
            FlowRow(horizontalArrangement = Arrangement.Center,
                modifier = Modifier.width(450.dp)
            ) {
                DisturbanceOptions.entries.forEach { disturbanceOption ->
                    RadioButtonWithText(
                        text = disturbanceOption.displayName,
                        isSelected = coverageUiState.disturbance[disturbanceOption] == true,
                        onClick = { 
                            updateDisturbanceOption(disturbanceOption)
                        }
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
                value = coverageUiState.observations.toString(),
                onValueChange = { updateUiState(coverageUiState.copy(observations = it)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                )
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
                    onClick = saveCoverage,
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

enum class DisturbanceOptions(val displayName: String) {
    INUNDATION("Inundación"),
    BURNING("Quema"),
    CHOP("Tala"),
    EROSION("Erosión"),
    MINING("Minería"),
    FREEWAY("Carretera"),
    AQUATIC("Más plantas acuáticas"),
    OTHER("Otro")
}

enum class CoverageOptions() {
    BD,
    RA,
    RB,
    PA,
    PL,
    CP,
    CT,
    VH,
    TD,
    IF
}


/*    :/  WoW  */
enum class SINO(val displayName: String) {
    YES("Sí"),
    NO("No")
}


@ScreenPreviews
@Composable
fun CoverageFormsScreenPreview() {
    LechendasAppTheme {
        CoverageFormsScreen(
            onBack = {},
            onMenuClick = {},
            onSearchClick = {},
            onSettingsClick = {},
            monitorLogId = 0
        )
    }
}