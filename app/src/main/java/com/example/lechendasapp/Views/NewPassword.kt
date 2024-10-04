package com.example.lechendasapp.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

// Entry point
@Composable
fun NewPassword(navController: NavController) {
    // Scaffold with custom header and footer
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { AppHeader(navController, "Crear nueva Contraseña") },  // Custom Header
        bottomBar = { AppFooter() }  // Footer
    ) { innerPadding ->
        MainBody(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

// Custom Header with Row and Images
@Composable
fun AppHeader(navController: NavController, stringT: String) {
    // Custom layout with images at the top
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
        ) {
            // First Image (larger and positioned behind)
            ImageWithTextOverlay(
                imageRes = R.drawable.vector_5svg,
                text = stringT,
                navController = navController,
            )

            // Second Image (overlap on top of the first image)
            Image(
                painter = painterResource(R.drawable.vector_6svg), // Replace with your image resource
                contentDescription = "Logo 2",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .width(130.dp)
                    .height(200.dp)
            )
        }
}

@Composable
fun ImageWithTextOverlay(
    imageRes: Int,
    text: String,
    navController: NavController,
    modifier: Modifier = Modifier // Optional modifier for external customization
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .width(1900.dp)
        )

        Column (
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ){
            TextButton(onClick = { navController.navigate("login_view")}) {
                Image(
                    painter = painterResource(R.drawable.keyboard_arrow_left),
                    contentDescription = null, // Content description for accessibility
                    modifier = Modifier
                        .size(40.dp) // Make the image fill the whole Box
                )
            }

            Text(
                text = text,
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 40.sp,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 0.dp),
            )
        }
        }
}


@Composable
fun SimpleInputBox(labelText: String) {
    // Estado para almacenar el valor ingresado por el usuario
    var text by remember { mutableStateOf("") }

    // OutlinedTextField para la caja de texto
    OutlinedTextField(
        value = text,
        onValueChange = { text = it }, // Actualizar el valor cuando el usuario escriba
        label = { Text(labelText) }, // Etiqueta que aparece en la caja (recibida como parámetro)
        singleLine = true, // Mantener el texto en una sola línea
        modifier = Modifier
            .fillMaxWidth() // Ocupar el ancho completo
            .padding(20.dp), // Agregar un padding alrededor de la caja de texto
        textStyle = MaterialTheme.typography.bodyLarge // Estilo del texto
    )
}


// Footer
@Composable
fun AppFooter() {
    // Custom layout with images at the top
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Image(
            painter = painterResource(R.drawable.vector_3svg), // Replace with your image resource
            contentDescription = "Logo 3",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .width(2000.dp)
        )
    }
}

@Composable
fun MainBody(
    navController: NavController,
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
                modifier = Modifier
                    .fillMaxWidth() // Inner column fills available width
                    .padding(top = 30.dp)

            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    Text(
                        text = "Tu nueva contraseña debe ser diferente a la que usaste anteriormente",
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 30.sp

                    )

                }


                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SimpleInputBox(labelText = "Nueva Contraseña")
                    SimpleInputBox(labelText = "Confirma Contraseña")

                }

            }

            // Button at the bottom
            Button(
                onClick = { navController.navigate("intro_view") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.green),
                    contentColor = Color.White,
                ),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .height(60.dp)
                    .width(200.dp)
            ) {
                Text(
                    text = "Guardar",
                    fontSize = 20.sp,
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun NewPasswordView(navController: NavController) {
    LechendasAppTheme {
        NewPassword(navController = navController)
    }
}

// Preview for Composable function
@ScreenPreviews
@Composable
fun NewPasswordPreview() {
    LechendasAppTheme {
        NewPassword(navController = NavController(LocalContext.current))
    }
}