package com.example.lechendasapp.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import com.example.lechendasapp.R
import com.example.lechendasapp.preview.ScreenPreviews
import com.example.lechendasapp.ui.theme.LechendasAppTheme
import com.example.lechendasapp.utils.InitialFooter
import com.example.lechendasapp.utils.SimpleInputBox
import com.example.lechendasapp.utils.TopBar2

@Composable
fun NewPasswordScreen(
    onBack: () -> Unit,
) {
    Scaffold(
        topBar = { TopBar2(onBack) },
        bottomBar = { InitialFooter() }
    ) { innerPadding ->
        NewPassBody(
            onBack = onBack,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun NewPassBody(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            NewPassContent(onBack)
        }
    }
}

@Composable
private fun NewPassContent(onBack: () -> Unit) {
    Text(
        text = "Ingresa tu nueva contraseña",
        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
    )
    SimpleInputBox(labelText = "Nueva Contraseña")
    SimpleInputBox(labelText = "Confirma Contraseña")
    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_extra_large)))
    Button(
        onClick = onBack,
        modifier = Modifier.Companion
            .width(dimensionResource(R.dimen.button_width))
            .height(dimensionResource(R.dimen.button_height))
    ) {
        Text(
            text = "Guardar",
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@ScreenPreviews
@Composable
fun NewPasswordPreview() {
    LechendasAppTheme {
        NewPasswordScreen(
            onBack = {},
        )
    }
}