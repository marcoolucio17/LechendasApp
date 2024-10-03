package com.example.lechendasapp.Views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lechendasapp.ui.theme.LechendasAppTheme
import com.example.lechendasapp.R // Import the R file that links your resources

// Entry point
@Composable
fun VerifyUserScreen() {
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
            .height(250.dp), // Adjust height of the header area
            // horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            // First Image (larger and positioned behind)
            ImageWithTextOverlay(
                imageRes = R.drawable.vector_5, // Your image resource
                text = "Verificación",        // Text to overlay
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
                .padding(top = 50.dp)
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
                modifier = Modifier
                    .padding(16.dp), // Add padding for spacing

            )
        }
    }
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
    var text by remember { mutableStateOf(TextFieldValue("")) }
    var text2 by remember { mutableStateOf(TextFieldValue("")) }
    var text3 by remember { mutableStateOf(TextFieldValue("")) }
    var text4 by remember { mutableStateOf(TextFieldValue("")) }

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
                        text = "Porfavor escribe el código de verificación enviado a",
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                        lineHeight = 30.sp

                    )

                    Text(
                        text = "juanperez@algo.com",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }

                // aquí irá para ingresar el código de verificación
                Column(
                    modifier = Modifier
                        .padding(top = 35.dp)
                ) {

                    Row( Modifier
                        .fillMaxWidth()
                    ) {
                        TextField(
                            value = text,
                            textStyle = TextStyle.Default.copy(fontSize = 60.sp, textAlign = TextAlign.Center),
                            onValueChange = { newText ->
                                text = newText
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp, start = 8.dp)
                                .height(100.dp)

                        )

                        TextField(
                            value = text2,
                            textStyle = TextStyle.Default.copy(fontSize = 60.sp, textAlign = TextAlign.Center),
                            onValueChange = { newText ->
                                text2 = newText
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp)
                                .height(100.dp)
                        )

                        TextField(
                            value = text3,
                            textStyle = TextStyle.Default.copy(fontSize = 60.sp, textAlign = TextAlign.Center),
                            onValueChange = { newText ->
                                text3 = newText
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp)
                                .height(100.dp)
                        )

                        TextField(
                            value = text4,
                            textStyle = TextStyle.Default.copy(fontSize = 60.sp, textAlign = TextAlign.Center),
                            onValueChange = { newText ->
                                text4 = newText
                            },
                            modifier = Modifier.weight(1f).height(100.dp).padding(end = 8.dp)
                        )
                    }


                }

                // link para reenviar
                Text(
                    text = "Reenviar código",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.dark_green),
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .padding(top = 30.dp)


                )


            }

            // Button at the bottom
            Button(
                onClick = { /* Action for the button */ },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.dark_green)),
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .height(60.dp)
                    .width(150.dp)
            ) {
                Text(
                    text = "VERIFICAR",
                    fontSize = 20.sp
                )
            }
        }
    }
}




// Preview for Composable function
@Preview(showBackground = true)
@Composable
fun VerifyUserScreenPreview() {
    VerifyUserScreen()
}
