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
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
        ) {
            val isTablet = maxWidth > 600.dp
            Scaffold(
                topBar = { AppHeader( isTablet ) },
                bottomBar = { AppFooter(isTablet) }
            ) { innerPadding ->
                MainBody(
                    modifier = Modifier.padding(innerPadding),
                    isTablet = isTablet
                )
            }
        }
    }
}

// Custom Header with Row and Images
@Composable
fun AppHeader(isTablet: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(if (isTablet) 350.dp else 250.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            ImageWithTextOverlay(
                imageRes = R.drawable.vector_5,
                text = "Verificación",
                modifier = Modifier.size(if (isTablet) 750.dp else 550.dp),
                isTablet = isTablet
            )
            Image(
                painter = painterResource(R.drawable.vector_1),
                contentDescription = "Logo 2",
                modifier = Modifier
                    .size(if (isTablet) 450.dp else 190.dp)
                    .align(Alignment.CenterStart)
                    .offset(x = if (isTablet) 400.dp else 250.dp)
            )
        }
    }
}


@Composable
fun ImageWithTextOverlay(
    imageRes: Int,
    text: String,
    modifier: Modifier = Modifier,
    isTablet: Boolean
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(550.dp)
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
        )

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxHeight()
                .padding(top = 50.dp)
        ) {
            TextButton(onClick = {}) {
                Image(
                    painter = painterResource(R.drawable.keyboard_arrow_left),
                    contentDescription = null,
                    modifier = Modifier
                        .size(if (isTablet) 70.dp else 40.dp)
                        .padding(if (isTablet) 10.dp else 0.dp)
                )
            }

            Text(
                text = text,
                color = Color.White,
                fontSize = if (isTablet) 60.sp else 36.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(if (isTablet) 50.dp else 16.dp),

            )
        }
    }
}

@Composable
fun AppFooter(isTablet: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = if (isTablet) 32.dp else 16.dp)
            .height(if (isTablet) 300.dp else 150.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.vector_3),
                contentDescription = "Logo 3",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (isTablet) 300.dp else 150.dp)
            )
        }
    }
}


@Composable
fun MainBody(
    modifier: Modifier = Modifier,
    isTablet: Boolean
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
                modifier = Modifier.fillMaxWidth().padding(top = if (isTablet) 60.dp else 30.dp)
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

                Text(
                    text = "Reenviar código",
                    fontSize = if (isTablet) 24.sp else 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.dark_green),
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.padding(top = if (isTablet) 40.dp else 30.dp)
                )
            }

            Button(
                onClick = {  },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.dark_green)),
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

@Preview(showBackground = true)
@Composable
fun VerifyUserScreenPreview() {
    VerifyUserScreen()
}
