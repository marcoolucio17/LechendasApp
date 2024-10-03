package com.example.lechendasapp.Views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
            MainBody(
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


// Preview for Composable function
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreePreview() {
    LoginScreen()
}
