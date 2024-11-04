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
import androidx.compose.ui.unit.dp
import com.example.lechendasapp.R
import com.example.lechendasapp.preview.ScreenPreviews
import com.example.lechendasapp.ui.theme.LechendasAppTheme
import com.example.lechendasapp.utils.BottomNavBar
import com.example.lechendasapp.utils.SimpleInputBox
import com.example.lechendasapp.utils.TopBar3


@Composable
fun VegetationFormsScreen(
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
        VegetationFormsContent(
            modifier = modifier.padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun VegetationFormsContent(

    modifier: Modifier = Modifier
) {
    var selectedCuadranteMain by remember { mutableStateOf(0) }
    var selectedCuadranteSecond by remember { mutableStateOf(0) }
    var selectedSubCuadrante by remember { mutableStateOf(0) }
    var selectedHabito by remember { mutableStateOf(0) }

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

//                        val containerColor by animateColorAsState(
//                            targetValue = if (selectedCuadranteMain == it.ordinal) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.surface,
//                            animationSpec = tween(durationMillis = 300)
//                        )
//
//                        val contentColor by animateColorAsState(
//                            targetValue = if (selectedCuadranteMain == it.ordinal) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
//                            animationSpec = tween(durationMillis = 300)
//                        )
//
//                        val borderColor by animateColorAsState(
//                            targetValue = if (selectedCuadranteMain == it.ordinal) MaterialTheme.colorScheme.primary else Color.Gray,
//                            animationSpec = tween(durationMillis = 300)
//                        )

                        Button(
                            onClick = { selectedCuadranteMain = it.ordinal },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedCuadranteMain == it.ordinal) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.surface,
                                contentColor = if (selectedCuadranteMain == it.ordinal) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                            ),
                            shape = MaterialTheme.shapes.small,
                            border = BorderStroke(1.dp, if (selectedCuadranteMain == it.ordinal) MaterialTheme.colorScheme.primary else Color.Gray),
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
//                        val containerColor by animateColorAsState(
//                            targetValue = if (selectedCuadranteSecond == it.ordinal) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.surface,
//                            animationSpec = tween(durationMillis = 300)
//                        )
//
//                        val contentColor by animateColorAsState(
//                            targetValue = if (selectedCuadranteSecond == it.ordinal) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
//                            animationSpec = tween(durationMillis = 300)
//                        )
//
//                        val borderColor by animateColorAsState(
//                            targetValue = if (selectedCuadranteSecond == it.ordinal) MaterialTheme.colorScheme.primary else Color.Gray,
//                            animationSpec = tween(durationMillis = 300)
//                        )
                        Button(
                            onClick = { selectedCuadranteSecond = it.ordinal },
                            colors = ButtonDefaults.buttonColors(
                                containerColor =  if (selectedCuadranteSecond == it.ordinal) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.surface,
                                contentColor = if (selectedCuadranteSecond == it.ordinal) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                            ),
                            shape = MaterialTheme.shapes.medium,
                            border = BorderStroke(1.dp, if (selectedCuadranteSecond == it.ordinal) MaterialTheme.colorScheme.primary else Color.Gray),
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
//                    val containerColor by animateColorAsState(
//                        targetValue = if (selectedSubCuadrante == it.ordinal) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.surface,
//                        animationSpec = tween(durationMillis = 300)
//                    )
//
//                    val contentColor by animateColorAsState(
//                        targetValue = if (selectedSubCuadrante == it.ordinal) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
//                        animationSpec = tween(durationMillis = 300)
//                    )
//
//                    val borderColor by animateColorAsState(
//                        targetValue = if (selectedSubCuadrante == it.ordinal) MaterialTheme.colorScheme.primary else Color.Gray,
//                        animationSpec = tween(durationMillis = 300)
//                    )
                    Button(
                        onClick = { selectedSubCuadrante = it.ordinal },
                        colors = ButtonDefaults.buttonColors(
                            containerColor =  if (selectedSubCuadrante == it.ordinal) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.surface,
                            contentColor =  if (selectedSubCuadrante == it.ordinal) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                        ),
                        shape = MaterialTheme.shapes.large,
                        border = BorderStroke(1.dp,  if (selectedSubCuadrante == it.ordinal) MaterialTheme.colorScheme.primary else Color.Gray,),
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
                    val containerColor by animateColorAsState(
                        targetValue = if (selectedHabito == it.ordinal) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.surface,
                        animationSpec = tween(durationMillis = 300)
                    )
                    val contentColor by animateColorAsState(
                        targetValue = if (selectedHabito == it.ordinal) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                        animationSpec = tween(durationMillis = 300)
                    )

                    val borderColor by animateColorAsState(
                        targetValue = if (selectedHabito == it.ordinal) MaterialTheme.colorScheme.primary else Color.Gray,
                        animationSpec = tween(durationMillis = 300)
                    )

                    Button(
                        onClick = { selectedHabito = it.ordinal },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = containerColor,
                            contentColor = contentColor,
                            ),
                        shape = MaterialTheme.shapes.large,
                        border = BorderStroke(1.dp, borderColor),
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
                labelText = "Nombre común"
            )
        }
        item {
            SimpleInputBox(
                labelText = "Nombre científico"
            )
        }

        item {
            SimpleInputBox(
                labelText = "Placa"
            )
        }

        item {
            SimpleInputBox(
                labelText = "Circunferencia (cm)"
            )
        }
        item {
            SimpleInputBox(
                labelText = "Distancia (mt)"
            )
        }
        item {
            SimpleInputBox(
                labelText = "Altura (mt)"
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
            onSettingsClick = {}
        )
    }
}