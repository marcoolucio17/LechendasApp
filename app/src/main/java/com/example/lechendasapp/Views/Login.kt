package com.example.lechendasapp.Views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lechendasapp.R
import com.example.lechendasapp.Views.AppFooter
import com.example.lechendasapp.Views.AppHeaderLogin
import com.example.lechendasapp.Views.MainBody
import com.example.lechendasapp.ui.theme.LechendasAppTheme


@Composable
fun LoginScreen() {
    LechendasAppTheme {
        // Scaffold with custom header and footer
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { AppHeaderLogin() },
            bottomBar = { AppFooter() }
        ) { innerPadding ->
            MainBodyLogin(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
fun AppHeaderLogin() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp) // Ajustar altura del header
    ) {

        // Decoración extra en el lado derecho
        Image(
            painter = painterResource(R.drawable.vector_1_login), // Reemplaza con tu imagen
            contentDescription = "Wave Decoration Right",
            contentScale = ContentScale.Crop, // Ajusta la escala de la imagen
            modifier = Modifier
                .fillMaxWidth() // Ocupa todo el ancho
                .align(Alignment.TopEnd)
        )

        // Primera capa de fondo (forma de ola)
        Image(
            painter = painterResource(R.drawable.vector_4), // Reemplaza con tu imagen
            contentDescription = "Wave Background 1",
            contentScale = ContentScale.Crop, // Ajusta la escala de la imagen
            modifier = Modifier
                .fillMaxWidth() // Ocupa todo el ancho
                .align(Alignment.TopStart)
        )

        TextButton(onClick = {}) {
            Image(
                painter = painterResource(R.drawable.keyboard_arrow_left),
                contentDescription = null, // Content description for accessibility
                modifier = Modifier
                    .size(40.dp) // Make the image fill the whole Boxz
            )
        }

        // Segunda capa de fondo (segunda ola)
        Image(
            painter = painterResource(R.drawable.vector_5_login), // Reemplaza con tu imagen
            contentDescription = "Wave Background 2",
            contentScale = ContentScale.Crop, // Ajusta la escala de la imagen
            modifier = Modifier
                .fillMaxWidth() // Ocupa todo el ancho
                .align(Alignment.TopStart)
        )

        // Texto en el centro
        Text(
            text = "Bienvenido",
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.W700,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(16.dp, 0.dp)
        )
    }
}

@Composable
fun MainBodyLogin(
    modifier: Modifier = Modifier, // Optional modifier for external customization
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
                modifier = Modifier.fillMaxWidth() // Inner column fills available width
                    .padding(top = 30.dp)

            ) {

                    Text(
                        text = "Inicia Sesión",
                        textAlign = TextAlign.Center,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.W700,
                        lineHeight = 30.sp

                    )

                    SimpleInputBox(labelText = "Email")
                    SimpleInputBox(labelText = "Contraseña")

            }

            Text(
                text = "Olvidaste la contraseña?",
                modifier = Modifier.align(Alignment.End),
                fontSize = 13.sp,
                color = Color.Green,
                fontWeight = FontWeight.W600
            )

            // Button at the bottom
            Button(
                onClick = { /* Action for the button */ },
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .height(50.dp)
                    .width(100.dp)
            ) {
                Text(text = "Guardar")
            }
        }
    }
}

// Preview for Composable function
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreePreview() {
    LoginScreen()
}
