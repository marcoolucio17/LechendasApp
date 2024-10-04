package com.example.lechendasapp.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.lechendasapp.R
import com.example.lechendasapp.preview.ScreenPreviews
import com.example.lechendasapp.ui.theme.LechendasAppTheme

// Entry point
@Composable
fun VerifyUserScreen(navController: NavController) {
    LechendasAppTheme {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
        ) {
            val isTablet = maxWidth > 600.dp
            Scaffold(
                topBar = { AppHeader(isTablet, navController) },
                bottomBar = { AppFooter() }
            ) { innerPadding ->
                MainBody(
                    modifier = Modifier.padding(innerPadding),
                    isTablet = isTablet,
                    navController = navController
                )
            }
        }
    }
}

// Custom Header with Row and Images
@Composable
fun AppHeader(isTablet: Boolean, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
    ) {
        ImageWithTextOverlay(
            imageRes = R.drawable.vector_5svg,
            text = "Verificación",
            isTablet = isTablet,
            navController = navController
        )
        Image(
            painter = painterResource(R.drawable.vector_6svg),
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
    modifier: Modifier = Modifier,
    isTablet: Boolean,
    navController: NavController
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

        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            TextButton(onClick = {navController.navigate("login_view")}) {
                Image(
                    painter = painterResource(R.drawable.keyboard_arrow_left),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
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
fun MainBody(
    modifier: Modifier = Modifier,
    isTablet: Boolean,
    navController: NavController
) {
    var text by remember { mutableStateOf(TextFieldValue("")) }
    var text2 by remember { mutableStateOf(TextFieldValue("")) }
    var text3 by remember { mutableStateOf(TextFieldValue("")) }
    var text4 by remember { mutableStateOf(TextFieldValue("")) }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(if (isTablet) 32.dp else 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = if (isTablet) 60.dp else 30.dp)
            ) {

                Text(
                    text = "Porfavor escribe el código de verificación enviado a",
                    textAlign = TextAlign.Center,
                    fontSize = if (isTablet) 32.sp else 24.sp,
                    lineHeight = if (isTablet) 40.sp else 30.sp
                )
                Text(
                    text = "juanperez@algo.com",
                    fontSize = if (isTablet) 32.sp else 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Column(
                    modifier = Modifier
                        .padding(top = if (isTablet) 50.dp else 35.dp)
                ) {
                    Row(Modifier.fillMaxWidth()) {

                        TextField(
                            value = text,
                            textStyle = TextStyle.Default.copy(
                                fontSize = 60.sp,
                                textAlign = TextAlign.Center
                            ),
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
                            textStyle = TextStyle.Default.copy(
                                fontSize = 60.sp,
                                textAlign = TextAlign.Center
                            ),
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
                            textStyle = TextStyle.Default.copy(
                                fontSize = 60.sp,
                                textAlign = TextAlign.Center
                            ),
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
                            textStyle = TextStyle.Default.copy(
                                fontSize = 60.sp,
                                textAlign = TextAlign.Center
                            ),
                            onValueChange = { newText ->
                                text4 = newText
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(100.dp)
                                .padding(end = 8.dp)
                        )
                    }
                }

                Text(
                    text = "Reenviar código",
                    fontSize = if (isTablet) 24.sp else 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.green_800),
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.padding(top = if (isTablet) 40.dp else 30.dp)
                )
            }

            Button(
                onClick = { navController.navigate("intro_view") },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.green_800)),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .height(if (isTablet) 80.dp else 60.dp)
                    .width(if (isTablet) 200.dp else 150.dp)
            ) {
                Text(
                    text = "VERIFICAR",
                    fontSize = if (isTablet) 24.sp else 20.sp
                )
            }
        }
    }
}


@Composable
fun VerifyUserView(navController: NavController) {
    LechendasAppTheme {
        VerifyUserScreen(navController = navController)
    }
}


@ScreenPreviews
@Composable
fun VerifyUserScreenPreview() {
    LechendasAppTheme {
        VerifyUserScreen(navController = NavController(LocalContext.current))
    }
}
