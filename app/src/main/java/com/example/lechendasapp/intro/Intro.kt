package com.example.lechendasapp.intro

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lechendasapp.R
import com.example.lechendasapp.ui.theme.LechendasAppTheme

@Composable
fun Intro(companyApp: String, modifier: Modifier = Modifier) {
    val image = painterResource(R.drawable.intro_img)
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Text(text = "$companyApp!")
        Image(painter = image, contentDescription = null)
        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults
                .buttonColors(
                    containerColor = colorResource(id=R.color.green_500),
                    contentColor = Color.White
                )


        ) {
            Text(text = "Continuar")
        }
    }
}

@Composable
fun IntroImage(companyApp: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                            colors = listOf(
                                colorResource(id=R.color.white),
                                colorResource(id=R.color.green_100)
                            ),

                            center = Offset(
                                500f,
                                1000f
                            ),
                            radius = 800f
            )
    )
    )
    Intro(
        companyApp = companyApp,
        modifier = Modifier
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun IntroPreview() {
    LechendasAppTheme {
        IntroImage(companyApp = stringResource(R.string.app_name))
    }
}

