package com.example.lechendasapp.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lechendasapp.R
import com.example.lechendasapp.preview.ScreenPreviews
import com.example.lechendasapp.ui.theme.LechendasAppTheme

@Composable
fun IntroScreen(
    onLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    val gradientBackground = painterResource(R.drawable.radial_gradient)
    Surface (
        modifier = modifier.fillMaxSize(),
    ) {
        Image(
            painter = gradientBackground,
            contentDescription = null,
            contentScale = ContentScale.Crop,

        )
        IntroContent(
            onLogin = onLogin,
        )
    }
}

@Composable
fun IntroContent(
    onLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    val image = painterResource(R.drawable.intro_img)
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.displayMedium,

        )
        Image(
            painter = image,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = { onLogin() },
            modifier = Modifier
                .width(200.dp)
                .height(60.dp),
        ) {
            Text(
                text = stringResource(R.string.iniciar_sesion),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IntroContentPreview() {
    LechendasAppTheme {
        IntroContent(
            onLogin = {},
            modifier = Modifier
        )
    }
}

@ScreenPreviews
@Composable
fun IntroScreenPreview() {
    LechendasAppTheme {
        IntroScreen(
            onLogin = {},
            modifier = Modifier
        )
    }
}

