package com.example.lechendasapp.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lechendasapp.R
import com.example.lechendasapp.data.model.Vegetation
import com.example.lechendasapp.utils.BottomNavBar
import com.example.lechendasapp.utils.DetailItem
import com.example.lechendasapp.utils.TopBar3
import com.example.lechendasapp.viewmodels.SearchVegetationViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchVegetationScreen(
    onBack: () -> Unit,
    currentRoute: String = "search",
    onSearch: () -> Unit,
    onHome: () -> Unit,
    onSettings: () -> Unit,
    onEdit: (Long, Long) -> Unit,
    monitorLogId: Long,
    viewModel: SearchVegetationViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    viewModel.setMonitorLogId(monitorLogId)
    val searchVegetationUiState = viewModel.searchVegetationUiState.collectAsState()

    Scaffold(
        topBar = { TopBar3(onBack = onBack) },
        bottomBar = {
            BottomNavBar(
                currentRoute = currentRoute,
                onHome = onHome,
                onSearch = onSearch,
                onSettings = onSettings
            )
        }
    ) { innerPadding ->
        SearchVegetationContent(
            modifier = modifier.padding(innerPadding),
            logList = searchVegetationUiState.value.vegetation,
            onDelete = viewModel::deleteVegetation,
            onEdit = onEdit
        )
    }
}


@Composable
fun SearchVegetationContent(
    modifier: Modifier = Modifier,
    logList: List<Vegetation>,
    onDelete: (Long) -> Unit,
    onEdit: (Long, Long) -> Unit
) {
    if (logList.isEmpty()) {
        Log.d("SearchVegetationScreen", "Empty List")
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            Text(
                text = "No hay registros"
            )
        }
    } else {
        Log.d("SearchGVegetationScreen", "Not Empty List")
        VegetationLogList(
            logList = logList,
            onDelete = onDelete,
            modifier = modifier,
            onEdit = onEdit
        )
    }
}

@Composable
fun VegetationLogList(
    logList: List<Vegetation>,
    onDelete: (Long) -> Unit,
    onEdit: (Long, Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 20.dp)
    ) {
        items(items = logList, key = { it.id }) { log ->
            SearchVegetationItem(
                log = log,
                onDelete = onDelete,
                onEdit = onEdit
            )
        }
    }
}

@Composable
fun SearchVegetationItem(
    log: Vegetation,
    onDelete: (Long) -> Unit,
    onEdit: (Long, Long) -> Unit,
    //modifier: Modifier = Modifier
) {
    val expanded = remember { mutableStateOf(false) } // Estado para el menú desplegable
    val expandedCard = remember { mutableStateOf(false) }
    Card(
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.onPrimary
        ),
        modifier = Modifier
            .padding(bottom = 8.dp)
            .clickable { expandedCard.value = !expandedCard.value }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp)
            ) {
                Text(
                    text = log.id.toString(),
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = log.commonName.toString(),
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            IconButton(
                onClick = { expanded.value = true } // Abre el menú
            ) {
                Icon(
                    painter = painterResource(R.drawable.more_vert),
                    contentDescription = "More options"
                )
                // Menú desplegable con opciones "Editar" y "Borrar"
                DropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false },
                ) {
                    DropdownMenuItem(
                        onClick = {
                            expanded.value = false
                            onEdit(log.id, log.monitorLogId)
                        },
                        text = { Text("Edit") }
                    )
                    DropdownMenuItem(
                        onClick = {
                            expanded.value = false
                            onDelete(log.id)
                        },
                        text = { Text("Borrar") },
                    )
                }
            }
        }
        AnimatedVisibility(
            visible = expandedCard.value,
            enter = expandVertically() + fadeIn(),
            exit = shrinkVertically() + fadeOut()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp, vertical = 10.dp)
            ) {
                DetailItem("Code", log.code)
                DetailItem("Quadrant", log.quadrant)
                DetailItem("Sub Quadrant", log.subQuadrant)
                DetailItem("Growth Habit", log.growthHabit)
                DetailItem("Scientific Name", log.scientificName ?: "")
                DetailItem("Plate", log.plate)
                DetailItem("Circumference", log.circumference.toString())
                DetailItem("Distance", log.distance.toString())
                DetailItem("Height", log.height.toString())
                DetailItem("Observations", log.observations ?: "")
            }
        }
    }
}