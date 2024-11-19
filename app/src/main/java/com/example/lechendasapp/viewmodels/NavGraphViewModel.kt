package com.example.lechendasapp.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.result.Credentials
import com.example.lechendasapp.data.model.AuthToken
import com.example.lechendasapp.data.repository.AuthTokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class NavGraphViewModel @Inject constructor(
    private val authTokenRepository: AuthTokenRepository
) : ViewModel() {
    private val _credentials = mutableStateOf<Credentials?>(null)
    val credentials: State<Credentials?> = _credentials

    private val _loggedIn = mutableStateOf(false)
    val loggedIn: State<Boolean> = _loggedIn

    private val _showLogoutDialog = mutableStateOf(false)
    val showLogoutDialog: State<Boolean> = _showLogoutDialog

    init {
        // Check for stored tokens when ViewModel is created
        viewModelScope.launch {
            authTokenRepository.getAuthToken().collect { token ->
                if (token != null) {
                    // Reconstruct Credentials object from stored data
                    _credentials.value = Credentials(
                        idToken = token.idToken,
                        accessToken = token.accessToken,
                        type = token.tokenType,
                        refreshToken = token.refreshToken,
                        expiresAt = token.expiresAt,
                        scope = token.scope
                    )
                    if (!isTokenExpired()) {
                        _loggedIn.value = true
                    } else {
                        // Optionally refresh the token here
                        logout()
                    }
                }
            }
        }
    }

    fun setCredentials(credentials: Credentials) {
        _credentials.value = credentials
        viewModelScope.launch {
            // Store all credentials information
            authTokenRepository.saveAuthToken(
                AuthToken(
                    idToken = credentials.idToken,
                    accessToken = credentials.accessToken,
                    tokenType = credentials.type,
                    refreshToken = credentials.refreshToken ?: "",
                    expiresAt = credentials.expiresAt,
                    scope = credentials.scope ?: ""
                )
            )
        }
        _loggedIn.value = true
    }

    fun logout() {
        viewModelScope.launch {
            authTokenRepository.clearAuthToken()
            _credentials.value = null
            _loggedIn.value = false
            _showLogoutDialog.value = false
        }
    }

    private fun isTokenExpired(): Boolean {
        return _credentials.value?.expiresAt?.before(Date()) != false
    }

    fun setShowLogoutDialog(show: Boolean) {
        _showLogoutDialog.value = show
    }

}