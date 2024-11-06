package com.example.lechendasapp.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.result.Credentials
import com.auth0.android.callback.Callback
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth0: Auth0
) : ViewModel() {

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
}
