package com.example.lechendasapp.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lechendasapp.preview.ScreenPreviews
import com.example.lechendasapp.ui.theme.LechendasAppTheme

// Entry point
@Composable
fun NewPasswordScreen(navController: NavController) {
    LechendasAppTheme {
        // Scaffold with custom header and footer
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { AppHeader(navController = navController, stringT = "Olvidaste tu contraseña?") },  // Custom Header
            bottomBar = { AppFooter() }  // Footer
        ) { innerPadding ->
            MainBody2(
                modifier = Modifier.padding(innerPadding),
                navController = navController
            )
        }
    }
}

@Composable
fun MainBody2(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Box(
        modifier = modifier
            .fillMaxSize() // Fill the maximum available width and height
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize() // Fill the maximum size
                .padding(16.dp), // Optional padding for spacing
            verticalArrangement = Arrangement.SpaceBetween // Space between inner column and button
        ) {
            // Inner column with content passed as parameter
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth() // Inner column fills available width
                    .padding(top = 30.dp)

            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    Text(
                        text = "Por favor escribe tu email para poder recibir un código de verificación",
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 30.sp

                    )

                }


                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SimpleInputBox(labelText = "Email")

                }

            }

            // Button at the bottom
            Button(
                onClick = { navController.navigate("login_view") },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .height(50.dp)
                    .width(150.dp), // Ancho completo del botón
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4C6A2B), // Color de fondo del botón (verde en este ejemplo)
                    contentColor = Color.White // Color del texto o contenido dentro del botón
                )
            ) {
                Text(text = "ENVIAR")
            }
        }
    }
}

@Composable
fun NewPasswordScreenView(navController: NavController) {
    LechendasAppTheme {
        NewPasswordScreen(navController = navController)
    }
}

// Preview for Composable function
@ScreenPreviews
@Composable
fun NewPasswordScreenPreview() {
    LechendasAppTheme {
        NewPasswordScreen(navController = NavController(LocalContext.current))
    }
}
