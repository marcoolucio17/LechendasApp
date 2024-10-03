package com.example.lechendasapp.Views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lechendasapp.ui.theme.LechendasAppTheme
import com.example.lechendasapp.R // Import the R file that links your resources
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme

// Entry point
@Composable
fun NewPasswordScreen() {
    LechendasAppTheme {
        // Scaffold with custom header and footer
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { AppHeader() },  // Custom Header
            bottomBar = { AppFooter() }  // Footer
        ) { innerPadding ->
            MainBody(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

// Custom Header with Row and Images
@Composable
fun AppHeader() {
    // Custom layout with images at the top
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(190.dp), // Adjust height of the header area
        // horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            // First Image (larger and positioned behind)
            ImageWithTextOverlay(
                imageRes = R.drawable.vector_5, // Your image resource
                text = "Crear nueva contraseña",        // Text to overlay
                modifier = Modifier.size(550.dp) // You can adjust the size if needed

            )

            // Second Image (overlap on top of the first image)
            Image(
                painter = painterResource(R.drawable.vector_1), // Replace with your image resource
                contentDescription = "Logo 2",
                modifier = Modifier
                    .size(190.dp)
                    .align(Alignment.CenterStart) // Align it to start (to overlap)
                    .offset(x = 250.dp) // Adjust offset to overlap with the first image

            )
        }
    }
}

@Composable
fun ImageWithTextOverlay(
    imageRes: Int, // Image resource ID
    text: String,  // Text to overlay
    modifier: Modifier = Modifier // Optional modifier for external customization
) {
    Box(
        modifier = modifier
            .fillMaxWidth() // Make the composable fill the width
            .height(550.dp) // Adjust the height as needed
    ) {
        // The image in the background
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null, // Content description for accessibility
            modifier = Modifier
                .fillMaxSize() // Make the image fill the whole Box
        )

        // The text overlay on top of the image
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxHeight()
                .padding(top = 0.dp)
        ) {
            TextButton(onClick = {}) {
                Image(
                    painter = painterResource(R.drawable.keyboard_arrow_left),
                    contentDescription = null, // Content description for accessibility
                    modifier = Modifier
                        .size(40.dp) // Make the image fill the whole Box
                )
            }

            Text(
                text = text,
                color = Color.White, // Make text white
                fontSize = 36.sp, // Increase font size
                fontWeight = FontWeight.Bold,
                lineHeight = 40.sp,
                modifier = Modifier
                    .padding(16.dp, 0.dp), // Add padding for spacing

            )
        }
    }
}


@Composable
fun SimpleInputBox() {
    // Estado para almacenar el valor ingresado por el usuario
    var text by remember { mutableStateOf("") }

    // OutlinedTextField para la caja de texto
    OutlinedTextField(
        value = text,
        onValueChange = { text = it }, // Actualizar el valor cuando el usuario escriba
        label = { Text("Nueva Contraseña") }, // Etiqueta que aparece en la caja
        singleLine = true, // Mantener el texto en una sola línea
        modifier = Modifier
            .fillMaxWidth() // Ocupar el ancho completo
            .padding(16.dp), // Agregar un padding alrededor de la caja de texto
        textStyle = MaterialTheme.typography.bodyLarge // Estilo del texto
    )
}

// Footer
@Composable
fun AppFooter() {
    // Custom layout with images at the top
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .height(150.dp), // Adjust height of the header area
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween, // Space between the images
            verticalAlignment = Alignment.CenterVertically
        ) {
            // First Image (enlarge image)
            Image(
                painter = painterResource(R.drawable.vector_3), // Replace with your image resource
                contentDescription = "Logo 3",
                modifier = Modifier.size(550.dp) // Set size for the first image
            )
        }
    }
}

@Composable
fun MainBody(
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
                ){
                    SimpleInputBox()
                    SimpleInputBox()
                }

            }

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
fun NewPasswordScreenPreview() {
    NewPasswordScreen()
    }
