package com.example.lechendasapp.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lechendasapp.R
import com.example.lechendasapp.preview.ScreenPreviews
import com.example.lechendasapp.ui.theme.LechendasAppTheme


@Composable
fun LoginScreen(navController: NavController) {
    // Scaffold with custom header and footer
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { AppHeaderLogin(navController) },
        bottomBar = { AppFooter() }
    ) { innerPadding ->
        MainBodyLogin(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun AppHeaderLogin(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    ) {
        // Decoración extra en el lado derecho
        Image(
            painter = painterResource(R.drawable.vector_1svg), // Reemplaza con tu imagen
            contentDescription = "Wave Decoration Right",
            contentScale = ContentScale.FillBounds, // Ajusta la escala de la imagen
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopEnd)
                .height(500.dp)
        )

        // Primera capa de fondo (forma de ola)
        Image(
            painter = painterResource(R.drawable.vector_4svg), // Reemplaza con tu imagen
            contentDescription = "Wave Background 1",
            contentScale = ContentScale.FillBounds, // Ajusta la escala de la imagen
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart)
        )

        // Segunda capa de fondo (segunda ola)
        Image(
            painter = painterResource(R.drawable.vector_2svg), // Reemplaza con tu imagen
            contentDescription = "Wave Background 2",
            contentScale = ContentScale.FillBounds, // Ajusta la escala de la imagen
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(vertical = 50.dp)
        ) {
            TextButton(onClick = {navController.navigate("intro_view")}) {
                Image(
                    painter = painterResource(R.drawable.keyboard_arrow_left),
                    contentDescription = null, // Content description for accessibility
                    modifier = Modifier
                        .size(40.dp) // Make the image fill the whole Boxz
                )
            }


            // Texto en el centro
            Text(
                text = "Bienvenido",
                color = Color.White,
                fontSize = 50.sp,
                fontWeight = FontWeight.W700,
                modifier = Modifier
                    .padding(16.dp, 0.dp)
            )
        }
    }
}

@Composable
fun MainBodyLogin(
    navController: NavController,
    modifier: Modifier = Modifier, // Optional modifier for external customization
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Inicia Sesión",
            textAlign = TextAlign.Center,
            fontSize = 23.sp,
            fontWeight = FontWeight.W700,
            lineHeight = 30.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 20.dp, vertical = 10.dp)
        )

        SimpleInputBox(labelText = "Email")
        SimpleInputBox(labelText = "Contraseña")

        Text(
            text = "Olvidaste la contraseña?",
            modifier = Modifier
                .align(Alignment.End)
                .padding(horizontal = 30.dp, vertical = 30.dp)
                .clickable { navController.navigate("new_password") },
            fontSize = 20.sp,
            color = colorResource(id = R.color.green_800),
            fontWeight = FontWeight.W600,
        )

        // Button at the bottom
        Button(
            onClick = {
                navController.navigate("intro_view")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.green),
                contentColor = Color.White,
            ),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(200.dp)
                .height(60.dp)
        ) {
            Text(
                text = "Guardar",
                fontSize = 20.sp
            )
        }

    }
}


@Composable
fun LoginView(navController: NavController) {
    LechendasAppTheme {
        LoginScreen(navController = navController)
    }
}

// Preview for Composable function
@ScreenPreviews
@Composable
fun LoginScreePreview() {
    LechendasAppTheme {
        LoginScreen(navController = NavController(LocalContext.current))
    }
}
