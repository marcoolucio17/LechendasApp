package com.example.lechendasapp.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.result.Credentials
import com.example.lechendasapp.data.model.User
import com.example.lechendasapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val email: String = "",
    val password: String = "",
)

fun User.toUserDetails(): LoginUiState = LoginUiState(
    email = this.email,
    password = this.password,
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val auth0: Auth0
) : ViewModel() {
    private val _loginUiState = mutableStateOf(LoginUiState())
    val loginUiState: State<LoginUiState> = _loginUiState

    fun loginWithUsernamePassword(username: String, password: String, onSuccess: (Credentials) -> Unit, onError: (String) -> Unit) {
        val authentication = AuthenticationAPIClient(auth0)
        authentication
            .login(username, password, "Username-Password-Authentication")
            .setConnection("Username-Password-Authentication")
            .setScope("openid profile email")
            .start(object : Callback<Credentials, AuthenticationException> {
                override fun onSuccess(result: Credentials) {
                    onSuccess(result)
                }

                override fun onFailure(error: AuthenticationException) {
                    Log.e("AuthError", "Error description: ${error.getDescription()}")
                    onError(error.getDescription() ?: "Unknown error")
                }
            })
    }

    //TODO: validame antes de loguear
    //get email and password from the users input
    fun updateUiState(newUserDetail: LoginUiState) {
        _loginUiState.value = newUserDetail
        Log.d("LoginViewModel", "updateUiState: $newUserDetail")
    }
}