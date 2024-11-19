package com.example.lechendasapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lechendasapp.R
import com.example.lechendasapp.preview.ScreenPreviews
import com.example.lechendasapp.ui.theme.LechendasAppTheme
import com.example.lechendasapp.viewmodels.NavGraphViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun IntroScreen(
    onLogin: () -> Unit,
    navigateToHome: () -> Unit,
    viewModel: NavGraphViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val loggedIn by viewModel.loggedIn

    LaunchedEffect(loggedIn) {
        if (loggedIn) {
            navigateToHome()
        }
    }

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
            isLoggedIn = loggedIn,
        )
    }
}

@Composable
fun IntroContent(
    onLogin: () -> Unit,
    isLoggedIn: Boolean,
    modifier: Modifier = Modifier
) {
    val image = painterResource(R.drawable.intro_img)
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    )
    {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.displayMedium,

        )
        Image(
            painter = image,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.height(40.dp))

        if (!isLoggedIn) {
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
        } else {
            // Display a loading animation while redirecting
            CircularProgressIndicator()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IntroContentPreview() {
    LechendasAppTheme {
        IntroContent(
            onLogin = {},
            isLoggedIn = false,
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
            navigateToHome = {},
            modifier = Modifier
        )
    }
}

