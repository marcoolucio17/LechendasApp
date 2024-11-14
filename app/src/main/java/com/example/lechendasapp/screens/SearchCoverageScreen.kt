package com.example.lechendasapp.screens

import android.util.Log
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
import com.example.lechendasapp.data.model.Coverage
import com.example.lechendasapp.utils.BottomNavBar
import com.example.lechendasapp.utils.TopBar3
import com.example.lechendasapp.viewmodels.SearchCoverageViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchCoverageScreen(
    onBack: () -> Unit,
    currentRoute: String = "search",
    onSearch: () -> Unit,
    onHome: () -> Unit,
    onSettings: () -> Unit,
    monitorLogId: Long,
    onEdit: (Long, Long) -> Unit,
    viewModel: SearchCoverageViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    viewModel.setMonitorLogId(monitorLogId)
    val searchCoverageUiState = viewModel.searchCoverageUiState.collectAsState()

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
        SearchCoverageContent(
            modifier = modifier.padding(innerPadding),
            logList = searchCoverageUiState.value.coverage,
            onDelete = viewModel::deleteCoverage,
            onEdit = onEdit
        )
    }
}


@Composable
fun SearchCoverageContent(
    modifier: Modifier = Modifier,
    logList: List<Coverage>,
    onDelete: (Long) -> Unit,
    onEdit: (Long, Long) -> Unit
) {
    if (logList.isEmpty()) {
        // Log.d("SearchCoverageScreen", "Empty List")
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
        // Log.d("SearchGCoverageScreen", "Not Empty List")
        CoverageLogList(
            logList = logList,
            onDelete = onDelete,
            onEdit = onEdit,
            modifier = modifier,
        )
    }
}

@Composable
fun CoverageLogList(
    logList: List<Coverage>,
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
            SearchCoverageItem(
                log = log,
                onDelete = onDelete,
                onEdit = onEdit,
                //modifier = modifier
            )
        }
    }
}

@Composable
fun SearchCoverageItem(
    log: Coverage,
    onDelete: (Long) -> Unit,
    onEdit: (Long, Long) -> Unit,
    //modifier: Modifier = Modifier
) {
    val expanded = remember { mutableStateOf(false) } // Estado para el menú desplegable
    Card(
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.onPrimary
        ),
        modifier = Modifier
            .padding(bottom = 8.dp)
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
                    text = log.tracking,
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
    }
}