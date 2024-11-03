package com.example.lechendasapp.views

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lechendasapp.R
import com.example.lechendasapp.preview.ScreenPreviews
import com.example.lechendasapp.ui.theme.LechendasAppTheme
import com.example.lechendasapp.utils.InitialFooter
import com.example.lechendasapp.utils.TopBar1
import com.example.lechendasapp.views.AuthenticationManager.AuthResponse
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


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
    viewModel: AuthViewModel = hiltViewModel(),
) {
//    val userDetail: UserCredentials = loginUiState.userCredentials

    val context = LocalContext.current

    val authenticationManager = remember { AuthenticationManager(context) }

    val coroutineScope = rememberCoroutineScope()

    val userName = remember { mutableStateOf("") }
    val userPass = remember { mutableStateOf("") }

    TokenDisplay()
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

@Composable
fun TokenDisplay(
    viewModel: AuthViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val tokenState = viewModel.tokenState.collectAsState().value
    val errorState = viewModel.errorState.collectAsState().value
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.fetchToken()
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (tokenState.isNullOrEmpty()) {
            Text(
                text = "Your Token:",
                style = MaterialTheme.typography.titleMedium
            )

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    SelectionContainer {
                        Text(
                            text = tokenState ?: "",
                            style = MaterialTheme.typography.bodySmall,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            scope.launch {
                                val clipboardManager = context.getSystemService(
                                    Context.CLIPBOARD_SERVICE
                                ) as ClipboardManager

                                val clip = ClipData.newPlainText("token", tokenState)
                                clipboardManager.setPrimaryClip(clip)

                                Toast.makeText(
                                    context,
                                    "Token copied to clipboard",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    ) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Copy Token")
                    }
                }
            }
        }

        if (errorState.isNullOrEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Text(
                    text = errorState ?: "",
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }
    }
}


@ScreenPreviews
@Composable
fun LoginScreenPreview() {
    LechendasAppTheme {
        LoginScreen(
            onBack = {},
            onLoginSuccess = {}
        )
    }
}
