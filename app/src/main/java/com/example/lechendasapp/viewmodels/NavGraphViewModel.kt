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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class NavGraphViewModel @Inject constructor(
    private val authTokenRepository: AuthTokenRepository
) : ViewModel() {
    private val _credentials = MutableStateFlow<Credentials?>(null)
    val credentials: StateFlow<Credentials?> = _credentials.asStateFlow()

    private val _loggedIn = MutableStateFlow(false)
    val loggedIn: StateFlow<Boolean> = _loggedIn.asStateFlow()

    private val _showLogoutDialog = MutableStateFlow(false)
    val showLogoutDialog: StateFlow<Boolean> = _showLogoutDialog.asStateFlow()

    init {
        checkInitialAuthStatus()
    }

    private fun checkInitialAuthStatus() {
        viewModelScope.launch {
            // Use async/coroutine to make initialization non-blocking
            val storedToken = withContext(Dispatchers.IO) {
                authTokenRepository.getAuthToken().first()
            }

            storedToken?.let { token ->
                val credentials = Credentials(
                    idToken = token.idToken,
                    accessToken = token.accessToken,
                    type = token.tokenType,
                    refreshToken = token.refreshToken,
                    expiresAt = token.expiresAt,
                    scope = token.scope
                )

                _credentials.value = credentials
                _loggedIn.value = !isTokenExpired(credentials)
            }
        }
    }

    private fun isTokenExpired(credentials: Credentials): Boolean {
        return credentials.expiresAt?.before(Date()) ?: true
    }


    fun setCredentials(credentials: Credentials) {
        _credentials.value = credentials
        viewModelScope.launch(Dispatchers.IO) {
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

    fun setShowLogoutDialog(show: Boolean) {
        _showLogoutDialog.value = show
    }

}