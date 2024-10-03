package com.example.lechendasapp.views.intro

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lechendasapp.R
import com.example.lechendasapp.preview.ScreenPreviews
import com.example.lechendasapp.ui.theme.LechendasAppTheme

@Composable
fun Intro(companyApp: String, modifier: Modifier = Modifier) {
    val image = painterResource(R.drawable.intro_img)
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(if (isLandscape) 40.dp else 80.dp))
        Text(
            text = "$companyApp!",
            fontSize = 60.sp,
            color = colorResource(id = R.color.green_800),
        )
        Spacer(modifier = Modifier.height(if (isLandscape) 20.dp else 100.dp))
        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.height(if (isLandscape) 40.dp else 80.dp))
        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.green),
                    contentColor = Color.White,
                ),
            modifier = Modifier
                .width(200.dp)
                .height(60.dp)
        ) {
            Text(
                text = "Iniciar sesión",
                fontSize = 20.sp,
            )
        }
    }
}

@Composable
fun IntroImage(companyApp: String, modifier: Modifier = Modifier) {
    val gradientBackground = painterResource(R.drawable.radial_gradient)
    Box {
        Image(
            painter = gradientBackground,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier.fillMaxSize()
        )
        Intro(
            companyApp = companyApp, modifier = Modifier
        )
    }
}

@ScreenPreviews
@Composable
fun IntroPreview() {
    LechendasAppTheme {
        IntroImage(companyApp = stringResource(R.string.app_name))
    }
}

