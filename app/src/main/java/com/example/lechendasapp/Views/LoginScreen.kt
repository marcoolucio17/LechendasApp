package com.example.lechendasapp.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.lechendasapp.R
import com.example.lechendasapp.preview.ScreenPreviews
import com.example.lechendasapp.ui.theme.LechendasAppTheme
import com.example.lechendasapp.utils.InitialFooter
import com.example.lechendasapp.utils.TopBar1
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@Composable
fun LoginScreen(
    onBack: () -> Unit,
    onLoginSuccess: () -> Unit,
    //viewModel: LoginViewModel = hiltViewModel<LoginViewModel>(),
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = { TopBar1(onBack, R.string.bienvenido, modifier) },
        bottomBar = { InitialFooter() },
    ) { innerPadding ->
        LoginBody(
//            loginUiState = viewModel.loginUiState.value,
//            onUserChange = viewModel::updateUiState,
//            onVerify = { viewModel.checkUserCredentials(onLoginSuccess) },
            modifier = Modifier.padding(innerPadding)
        )
    }
}


@Composable
fun LoginBody(
//    loginUiState: LoginUiState,
//    onUserChange: (UserCredentials) -> Unit,
//    onVerify: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            LoginContent(
//                loginUiState,
//                onUserChange,
//                onVerify
            )
        }
    }
}

@Composable
private fun LoginContent(
//    loginUiState: LoginUiState,
//    onUserChange: (UserCredentials) -> Unit,
//    onVerify: () -> Unit
) {
//    val userDetail: UserCredentials = loginUiState.userCredentials

    val context = LocalContext.current

    val authenticationManager = remember { AuthenticationManager(context) }

    val coroutineScope = rememberCoroutineScope()

    val userName = remember { mutableStateOf("") }
    val userPass = remember { mutableStateOf("") }

    Text(
        text = stringResource(R.string.iniciar_sesion),
        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
    )
    OutlinedTextField(
        value = userName.value, // userDetail.email,
        onValueChange = { userName.value = it }, // { onUserChange(userDetail.copy(email = it)) },
        label = { Text(text = "Email") },
        singleLine = true,
        modifier = Modifier
            .width(450.dp)
            .padding(dimensionResource(R.dimen.padding_extra_large))
    )
    TextField(
        value = userPass.value, // userDetail.password,
        onValueChange = {
            userPass.value = it
        }, // { onUserChange(userDetail.copy(password = it)) },
        label = { Text("Contraseña") },
        singleLine = true,
        modifier = Modifier
            .width(450.dp)
            .padding(dimensionResource(R.dimen.padding_extra_large))
    )
    Text(
        text = stringResource(R.string.olvidaste_la_contrasena),
        modifier = Modifier.Companion
            .padding(
                horizontal = dimensionResource(R.dimen.padding_large),
                vertical = dimensionResource(R.dimen.padding_large)
            )
            .clickable {
                //TODO: Ir a pantalla de recuperar contraseña
            },
    )
    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_extra_large)))
    Button(
        onClick = {
            authenticationManager.loginWithEmail(
                email = userName.value,
                password = userPass.value
            ).onEach { response ->
                if (response is AuthResponse.Success) {
                    //TODO: Ir a pantalla principal
                }
            }
                .launchIn(coroutineScope)
        }, //onVerify,
        modifier = Modifier
            .width(dimensionResource(R.dimen.button_width))
            .height(dimensionResource(R.dimen.button_height))
    ) {
        Text(
            text = stringResource(R.string.entrar),
            style = MaterialTheme.typography.titleLarge
        )
    }
    Button(
        onClick = {
            authenticationManager.signInWithGoogle().onEach {
                response ->
                if (response is AuthResponse.Success) {
                    //TODO: Ir a pantalla principal
                }
            }
                .launchIn(coroutineScope)
        }, //onVerify,
        modifier = Modifier
            .width(dimensionResource(R.dimen.button_width))
            .height(dimensionResource(R.dimen.button_height))
    ) {
        Text(
            text = "GOOGLE",
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@ScreenPreviews
@Composable
fun LoginScreePreview() {
    LechendasAppTheme {
        LoginScreen(
            onBack = {},
            onLoginSuccess = {}
        )
    }
}
