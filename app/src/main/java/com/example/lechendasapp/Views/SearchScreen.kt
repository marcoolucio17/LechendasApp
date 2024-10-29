package com.example.lechendasapp.views

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.automirrored.sharp.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lechendasapp.R
import com.example.lechendasapp.data.model.MonitorLog
import com.example.lechendasapp.preview.ScreenPreviews
import com.example.lechendasapp.ui.theme.LechendasAppTheme
import com.example.lechendasapp.utils.BottomNavBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onBack: () -> Unit,
    currentRoute: String = "search",
    onSearch: () -> Unit,
    onHome: () -> Unit,
    onSettings: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val searchUiState = viewModel._searchUiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        topBar = { SearchTopBar(onBack = onBack) },
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
            logList = searchUiState.value.log
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopBar(
    onBack: () -> Unit,
    title: String = "Búsqueda"
) {
    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = onBack
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Sharp.ArrowBack, contentDescription = null
                )
            }
        },
        title = { Text(text = title) },
        actions = { // Add the actions parameter for additional icons
            IconButton(onClick = {/*TODO*/ }) { // Set up onClick for the menu icon
                Icon(
                    painter = painterResource(id = R.drawable.more_vert), // Use a three-dot icon resource
                    contentDescription = "More options"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
    )
}

@Composable
fun SearchContent(
    modifier: Modifier = Modifier,
    logList: List<MonitorLog>
) {
        if (logList.isEmpty()) {
            Text(
                text = "No hay registros"
            )
        } else {
            LogList(
                logList = logList,
                modifier = modifier
            )
        }
}

@Composable
fun LogList(
    logList: List<MonitorLog>,
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
            )
        }
    }
}

@Composable
fun SearchItem(
    log: MonitorLog,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.onPrimary
        ),
        modifier = Modifier.padding(bottom = 20.dp)
    )
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            //State
            Icon(
                imageVector = Icons.Filled.Notifications,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primaryContainer,
                modifier = Modifier
                    .size(50.dp)
                    .background(
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        shape = MaterialTheme.shapes.small
                    )
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 10.dp)
            ) {
                Text(
                    //ID
                    //text = "#FM00001",
                    text = log.id.toString(),
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    //TIPO DE OBSERVACIÓN
                    //text = "Fauna de Transectos",
                    text = log.observations ?: "Fauna de Transectos",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    //text = "Date: 15/10/2024 @ 4:20",
                    text = log.dateMillis.toString(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            IconButton(
                onClick = { /*TODO*/ },
            ) {
                Icon(painter = painterResource(R.drawable.more_vert), contentDescription = null)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchItemPreview() {
    LechendasAppTheme {
        SearchItem(
            log = MonitorLog(1, 15, 15, 15, "15", "15", "15", "15", "15")
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LogListPreview() {
    LechendasAppTheme {
        LogList(
            logList = listOf(
                MonitorLog(1, 15, 15, 15, "15", "15", "15", "15", "15"),
                MonitorLog(2, 15, 15, 15, "15", "15", "15", "15", "15"),
                MonitorLog(3, 15, 15, 15, "15", "15", "15", "15", "15"),
                MonitorLog(4, 15, 15, 15, "15", "15", "15", "15", "15")
            )
        )
    }
}


//@ScreenPreviews
//@Composable
//fun PreviewScreen() {
//    LechendasAppTheme {
//        SearchScreen(
//            onBack = {},
//            currentRoute = "search",
//            onSearch = {},
//            onHome = {},
//            onSettings = {},
//        )
//    }
//}