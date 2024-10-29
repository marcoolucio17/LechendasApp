package com.example.lechendasapp.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.lechendasapp.R
import com.example.lechendasapp.preview.ScreenPreviews
import com.example.lechendasapp.ui.theme.LechendasAppTheme
import com.example.lechendasapp.utils.InitialFooter
import com.example.lechendasapp.utils.SimpleInputBox
import com.example.lechendasapp.utils.TopBar2

@Composable
fun ForgotPasswordScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = { TopBar2(onBack = onBack) },
        bottomBar = { InitialFooter() }
    ) { innerPadding ->
        ForgetBody(
            onBack = onBack,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Composable
fun ForgetBody(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            ForgetContent(onBack)
        }
        Button(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .width(dimensionResource(R.dimen.button_width))
                .height(dimensionResource(R.dimen.button_height))
                .absoluteOffset(y= (-90).dp)
        ) {
            Text(
                text = stringResource(R.string.enviar),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Composable
private fun ForgetContent(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Text(
        text = "Escribe tu email para enviarte un código de verificación",
        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
    )
    SimpleInputBox(labelText = "Email")
}


@ScreenPreviews
@Composable
fun ForgotPasswordScreenPreview() {
    LechendasAppTheme {
        ForgotPasswordScreen(
            onBack = {},
        )
    }
}
