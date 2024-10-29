package com.example.lechendasapp.utils

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.lechendasapp.navigation.LechendasDestinations
import com.example.lechendasapp.ui.theme.LechendasAppTheme

@Composable
fun BottomNavBar(
    currentRoute: String?,
    onHome: () -> Unit,
    onSearch: () -> Unit,
    onSettings: () -> Unit,
    windowInsets: WindowInsets = NavigationBarDefaults.windowInsets,
    modifier: Modifier = Modifier,
) {
    val items = listOf("Home", "Búsqueda", "Configuración")
    val selectedIcons = listOf(Icons.Filled.Home, Icons.Filled.Search, Icons.Filled.Settings)
    val unselectedIcons =
        listOf(Icons.Outlined.Home, Icons.Outlined.Search, Icons.Outlined.Settings)
    val routes = listOf(
        LechendasDestinations.HOME_ROUTE,
        LechendasDestinations.SEARCH_ROUTE,
        LechendasDestinations.FORMULARY_ROUTE //TODO: Cambiar correctamente
    )

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        //routes[index] may be incorrect
                        if (currentRoute == routes[index]) selectedIcons[index] else unselectedIcons[index],
                        contentDescription = item
                    )
                },
                label = { Text(item) },
                selected = currentRoute == routes[index],
                onClick = {
                    if (currentRoute != routes[index]) {
                        when (index) {
                            0 -> onHome()
                            1 -> onSearch()
                            2 -> onSettings()
                        }
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavigationBarScreen() {
    LechendasAppTheme {
        BottomNavBar(
            currentRoute = "home",
            onHome = {},
            onSearch = {},
            onSettings = {}
        )
    }
}