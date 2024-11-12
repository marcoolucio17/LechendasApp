package com.example.lechendasapp.screens

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.lechendasapp.R
import com.example.lechendasapp.data.model.User
import com.example.lechendasapp.data.repository.UserRepository
import com.example.lechendasapp.preview.ScreenPreviews
import com.example.lechendasapp.ui.theme.LechendasAppTheme
import com.example.lechendasapp.utils.InitialFooter
import com.example.lechendasapp.utils.TopBar1
import com.example.lechendasapp.viewmodels.LoginUiState
import com.example.lechendasapp.viewmodels.LoginViewModel
import com.example.lechendasapp.viewmodels.UserCredentials
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


@Composable
fun LoginScreen(
    onBack: () -> Unit,
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel<LoginViewModel>(),
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = { TopBar1(onBack, R.string.bienvenido, modifier) },
        bottomBar = { InitialFooter() },
    ) { innerPadding ->
        LoginBody(
            loginUiState = viewModel.loginUiState.value,
            onUserChange = viewModel::updateUiState,
            onVerify = onLoginSuccess,
            //onVerify = { viewModel.checkUserCredentials(onLoginSuccess) },
            modifier = Modifier.padding(innerPadding)
        )
    }
}


@Composable
fun LoginBody(
    loginUiState: LoginUiState,
    onUserChange: (UserCredentials) -> Unit,
    onVerify: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box (modifier = modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        ) {
            LoginContent(loginUiState, onUserChange, onVerify)
//            LoginContent({})
        }
    }
}

@Composable
private fun LoginContent(
    loginUiState: LoginUiState,
    onUserChange: (UserCredentials) -> Unit,
    onVerify: () -> Unit
) {
    val userDetail: UserCredentials = loginUiState.userCredentials
    Text(
        text = stringResource(R.string.iniciar_sesion),
        style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
    )
    OutlinedTextField(
        value = userDetail.email,
        onValueChange = { onUserChange(userDetail.copy(email = it)) },
        label = { Text(text = "Email") },
        singleLine = true,
        modifier = Modifier
            .width(450.dp)
            .padding(dimensionResource(R.dimen.padding_extra_large))
    )
    TextField(
        value = userDetail.password,
        onValueChange = { onUserChange(userDetail.copy(password = it)) },
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
        onClick = onVerify,
        modifier = Modifier
            .width(dimensionResource(R.dimen.button_width))
            .height(dimensionResource(R.dimen.button_height))
    ) {
        Text(
            text = stringResource(R.string.entrar),
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@ScreenPreviews
@Composable
fun LoginScreePreview() {
    val previewViewModel = rememberPreviewLoginViewModel()

    LechendasAppTheme {
        LoginScreen(
            onBack = {},
            onLoginSuccess = {},
            viewModel = previewViewModel
        )
    }
}

class MockUserRepository : UserRepository {
    override suspend fun getUserByEmail(email: String): User? {
        // Return a sample user or null as needed for preview purposes
        return User(
            email = "test@example.com",
            password = "password123",
            id = 1,
            firstName = "dante",
            lastName = "Ferreira",
            birthDate = "sd",
            country = "País",
            occupation = "si",
            height = "1",
            team = 1,
            role = "lad"
        )
    }

    override suspend fun insertUser(user: User) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUser(user: User) {
        TODO("Not yet implemented")
    }

    override fun getUsersStream(): Flow<List<User>> {
        // Return an empty or sample flow for the preview
        return flowOf(emptyList())
    }

    override fun getIndividualUserStream(userId: Long): Flow<User> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllUsers(): List<User> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserById(userId: Long): User? {
        TODO("Not yet implemented")
    }
}

@Composable
fun rememberPreviewLoginViewModel(): LoginViewModel {
    val mockUserRepository = MockUserRepository()
    return remember {
        LoginViewModel(mockUserRepository).apply {
            // Initialize with sample data for the preview
            updateUiState(UserCredentials(email = "preview@example.com", password = "password"))
        }
    }
}