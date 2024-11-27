package com.example.lechendasapp.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lechendasapp.R
import com.example.lechendasapp.data.model.MonitorLog
import com.example.lechendasapp.data.repository.MonitorLogRepository
import com.example.lechendasapp.ui.theme.LechendasAppTheme
import com.example.lechendasapp.utils.BottomNavBar
import com.example.lechendasapp.utils.TopBar3
import com.example.lechendasapp.viewmodels.SearchViewModel
import com.example.lechendasapp.utils.DetailItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onBack: () -> Unit,
    currentRoute: String = "search",
    onSearch: () -> Unit,
    onHome: () -> Unit,
    onSettings: () -> Unit,
    onTransectClick: (Long) -> Unit,
    onClimateClick: (Long) -> Unit,
    onCoverageClick: (Long) -> Unit,
    onTrapClick: (Long) -> Unit,
    onVegetationClick: (Long) -> Unit,
    viewModel: SearchViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val searchUiState = viewModel.searchUiState.collectAsState()
    val searchQuery = viewModel.searchQuery.collectAsState()

    Scaffold(
        topBar = {
            Column  {
                TopBar3(onBack = onBack, showMenu = false)
                TextField(
                    value = searchQuery.value,
                    onValueChange = viewModel::updateSearchQuery,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    placeholder = { Text(text = "Search logs...") },
                    leadingIcon = {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    },
                    singleLine = true,
                )
            }
        },
        bottomBar = {
            BottomNavBar(
                currentRoute = currentRoute,
                onHome = onHome,
                onSearch = onSearch,
                onSettings = onSettings
            )
        }
    ) { innerPadding ->
        SearchContent(
            modifier = modifier.padding(innerPadding),
            logList = searchUiState.value.log,
            onDelete = viewModel::deleteMonitorLog,
            onTransectClick = onTransectClick,
            onClimateClick = onClimateClick,
            onCoverageClick = onCoverageClick,
            onTrapClick = onTrapClick,
            onVegetationClick = onVegetationClick,
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SearchContent(
    modifier: Modifier = Modifier,
    logList: List<MonitorLog>,
    onTransectClick: (Long) -> Unit,
    onClimateClick: (Long) -> Unit,
    onCoverageClick: (Long) -> Unit,
    onTrapClick: (Long) -> Unit,
    onVegetationClick: (Long) -> Unit,
    onDelete: (Long) -> Unit,
) {
    if (logList.isEmpty()) {
        Log.d("SearchScreen", "Empty List")
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
        Log.d("SearchScreen", "Not Empty List")
        LogList(
            logList = logList,
            onDelete = onDelete,
            onTransectClick = onTransectClick,
            onClimateClick = onClimateClick,
            onCoverageClick = onCoverageClick,
            onTrapClick = onTrapClick,
            onVegetationClick = onVegetationClick,
            modifier = modifier,
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LogList(
    logList: List<MonitorLog>,
    onDelete: (Long) -> Unit,
    onTransectClick: (Long) -> Unit,
    onClimateClick: (Long) -> Unit,
    onCoverageClick: (Long) -> Unit,
    onTrapClick: (Long) -> Unit,
    onVegetationClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 20.dp)
    ) {
        items(items = logList, key = { it.id }) { log ->
            SearchItem(
                log = log,
                onDelete = onDelete,
                onTransectClick = onTransectClick,
                onClimateClick = onClimateClick,
                onCoverageClick = onCoverageClick,
                onTrapClick = onTrapClick,
                onVegetationClick = onVegetationClick,
                //modifier = modifier
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SearchItem(
    log: MonitorLog,
    onDelete: (Long) -> Unit,
    onTransectClick: (Long) -> Unit,
    onClimateClick: (Long) -> Unit,
    onCoverageClick: (Long) -> Unit,
    onTrapClick: (Long) -> Unit,
    onVegetationClick: (Long) -> Unit,
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
                    text = log.logType,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = log.dateMillis.toReadableDate(),
                    style = MaterialTheme.typography.bodySmall
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
                            when (log.logType) {
                                TipoRegistro.TRANSECTOS.displayName -> onTransectClick(log.id)
                                TipoRegistro.PUNTO_CONTEO.displayName ->  onTransectClick(log.id)
                                TipoRegistro.BUSQUEDA_LIBRE.displayName ->  onTransectClick(log.id)
                                TipoRegistro.VALIDACION_COBERTURA.displayName -> onCoverageClick(log.id)
                                TipoRegistro.PARCELA_VEGETACION.displayName -> onVegetationClick(log.id)
                                TipoRegistro.CAMARAS_TRAMPA.displayName -> onTrapClick(log.id)
                                TipoRegistro.VARIABLES_CLIMATICAS.displayName -> onClimateClick(log.id)
                            }
                        },
                        text = { Text("View") }
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
                val (latitude, longitude) = log.gpsCoordinates.split(", ").map { it.trim() }
                DetailItem("GPS Lat", latitude)
                DetailItem("GPS Lon", longitude)
                DetailItem("Climate Type", log.climateType)
                DetailItem("Seasons", log.seasons)
                DetailItem("Zone", log.zone)
                DetailItem("User ID", log.userId.toString().take(5))
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchItemPreview() {
    LechendasAppTheme {
        SearchItem(
            log = MonitorLog(1, "0", 15, "15", "15", "15", "15", "15", "15"),
            onDelete = {},
            onTransectClick = {},
            onClimateClick = {},
            onCoverageClick = {},
            onTrapClick = {},
            onVegetationClick = {},
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LogListPreview() {
    LechendasAppTheme {
        LogList(
            logList = listOf(
                MonitorLog(1, "15", 15, "15", "15", "15", "15", "15", "15"),
                MonitorLog(2, "15", 15, "15", "15", "15", "15", "15", "15"),
                MonitorLog(3, "15", 15, "15", "15", "15", "15", "15", "15"),
                MonitorLog(4, "15", 15, "15", "15", "15", "15", "15", "15")
            ),
            onDelete = {},
            onTransectClick = {},
            onClimateClick = {},
            onCoverageClick = {},
            onTrapClick = {},
            onVegetationClick = {},
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun Long.toReadableDate(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        .withZone(ZoneId.systemDefault()) // Use the system's time zone
    return formatter.format(Instant.ofEpochMilli(this))
}

