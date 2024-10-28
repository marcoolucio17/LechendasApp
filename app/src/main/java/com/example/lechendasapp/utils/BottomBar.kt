package com.example.lechendasapp.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.lechendasapp.preview.ScreenPreviews
import com.example.lechendasapp.ui.theme.LechendasAppTheme
import com.example.lechendasapp.views.ForgotPasswordScreen

@Composable
fun NavigationBar(
    modifier: Modifier = Modifier,
    windowInsets: WindowInsets = NavigationBarDefaults.windowInsets,
    content: @Composable RowScope.() -> Unit
) {
    // Usa el Modifier para aplicar los estilos deseados
    Row(
        modifier = modifier
            .padding(windowInsets.asPaddingValues())
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Contenido proporcionado por el lambda content
        content()
    }
}

@Composable
fun MyScreen() {
    Column {
        // Contenido de tu pantalla

        NavigationBar {
            // Ejemplo de contenido de la barra de navegación
            IconButton(onClick = { /* Acción para el primer botón */ }) {
                Icon(Icons.Filled.Home, contentDescription = "Inicio")
            }
            IconButton(onClick = { /* Acción para el segundo botón */ }) {
                Icon(Icons.Filled.Favorite, contentDescription = "Favoritos")
            }
            IconButton(onClick = { /* Acción para el tercer botón */ }) {
                Icon(Icons.Filled.Settings, contentDescription = "Configuraciones")
            }
        }
    }
}


@Preview
@Composable
fun NavigationBarScreen() {
    LechendasAppTheme {
        MyScreen()
    }
}