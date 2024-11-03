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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.lechendasapp.R
import com.example.lechendasapp.preview.ScreenPreviews
import com.example.lechendasapp.ui.theme.LechendasAppTheme
import com.example.lechendasapp.utils.BottomNavBar
import com.example.lechendasapp.utils.SimpleInputBox
import com.example.lechendasapp.utils.TopBar3

@Composable
fun TrapFormsScreen(
    currentRoute: String = "trap",
    onBack: () -> Unit,
    onMenuClick: () -> Unit,
    onSearchClick: () -> Unit,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
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
            modifier = modifier.padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TrapFormsContent(

    modifier: Modifier = Modifier
) {
    val selectedChecks = remember {
        mutableStateMapOf<CheckList, Boolean>().apply {
            CheckList.entries.forEach { this[it] = false } // initialize all as unselected
        }
    }
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
            )
        }
        item {
            SimpleInputBox(
                labelText = "Nombre cámara"
            )
        }
        item {
            SimpleInputBox(
                labelText = "Placa cámara"
            )
        }
        item {
            SimpleInputBox(
                labelText = "Placa Guaya"
            )
        }
        item {
            SimpleInputBox(
                labelText = "Ancho camino (mt)"
            )
        }
        item {
            SimpleInputBox(
                labelText = "Fecha de instalación"
            )
        }
        item {
            SimpleInputBox(
                labelText = "Distancia de objetivo (mt)"
            )
        }
        item {
            SimpleInputBox(
                labelText = "Altura del lente (mt)"
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
                            checked = selectedChecks[check] == true,
                            onCheckedChange = { isChecked ->
                                selectedChecks[check] = isChecked
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
                modifier = Modifier.height(150.dp)
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
                    modifier = Modifier

                        .height(dimensionResource(R.dimen.small_button_height))
                        .weight(1f)
                ) {
                    Text(
                        text = "Atrás",
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                Button(
                    onClick = { /*TODO*/ },
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
            onSettingsClick = {}
        )
    }
}