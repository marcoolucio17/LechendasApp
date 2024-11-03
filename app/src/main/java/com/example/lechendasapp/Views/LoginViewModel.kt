package com.example.lechendasapp.views

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lechendasapp.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.Firebase
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AuthenticationManager @Inject constructor(
    @ApplicationContext private val context: Context
)  {
    private val auth = Firebase.auth

    fun createAccountWithEmail(email: String, password: String): Flow<AuthResponse> = callbackFlow {
        auth.createUserWithEmailAndPassword(email, password)

            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    trySend(AuthResponse.Success)
                } else {
                    trySend(AuthResponse.Error(message = task.exception?.message ?: ""))

                }
            }

        awaitClose()
    }

    private fun createNonce(): String{
        val rawNonce = UUID.randomUUID().toString()
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)

        return digest.fold("") {str, it ->
            str + "%02x".format(it)}
    }

    // Modified login method to get token after successful login
    fun loginWithEmail(email: String, password: String): Flow<AuthResponse> = callbackFlow {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Get token immediately after successful login
                    auth.currentUser?.getIdToken(true)
                        ?.addOnCompleteListener { tokenTask ->
                            if (tokenTask.isSuccessful) {
                                val token = tokenTask.result?.token
                                if (token != null) {
                                    trySend(AuthResponse.TokenSuccess(token))
                                } else {
                                    trySend(AuthResponse.Error("Token is null"))
                                }
                            } else {
                                trySend(AuthResponse.Error(tokenTask.exception?.message ?: ""))
                            }
                            close()
                        }
                } else {
                    trySend(AuthResponse.Error(message = task.exception?.message ?: ""))
                    close()
                }
            }
        awaitClose()
    }

    // Modified Google sign in to get token after successful login
    fun signInWithGoogle(): Flow<AuthResponse> = callbackFlow {
        try {
            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(context.getString(R.string.web_client_id))
                .setAutoSelectEnabled(false)
                .setNonce(createNonce())
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val credentialManager = CredentialManager.create(context)
            val result = credentialManager.getCredential(
                context = context,
                request = request
            )

            val credential = result.credential
            if (credential is CustomCredential &&
                credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                try {
                    val googleIdTokenCredential = GoogleIdTokenCredential
                        .createFrom(credential.data)

                    val firebaseCredential = GoogleAuthProvider
                        .getCredential(
                            googleIdTokenCredential.idToken,
                            null
                        )

                    auth.signInWithCredential(firebaseCredential)
                        .addOnCompleteListener { signInTask ->
                            if (signInTask.isSuccessful) {
                                // Get token after successful Google sign in
                                auth.currentUser?.getIdToken(true)
                                    ?.addOnCompleteListener { tokenTask ->
                                        if (tokenTask.isSuccessful) {
                                            val token = tokenTask.result?.token
                                            if (token != null) {
                                                trySend(AuthResponse.TokenSuccess(token))
                                            } else {
                                                trySend(AuthResponse.Error("Token is null"))
                                            }
                                        } else {
                                            trySend(AuthResponse.Error(tokenTask.exception?.message ?: ""))
                                        }
                                        close()
                                    }
                            } else {
                                trySend(AuthResponse.Error(message = signInTask.exception?.message ?: ""))
                                close()
                            }
                        }
                } catch (e: GoogleIdTokenParsingException) {
                    trySend(AuthResponse.Error(message = e.message ?: ""))
                    close()
                }
            }
        } catch (e: Exception) {
            trySend(AuthResponse.Error(message = e.message ?: ""))
            close()
        }
        awaitClose()
    }

    fun getCurrentUserToken(): Flow<AuthResponse> = callbackFlow {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            currentUser.getIdToken(true)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val token = task.result?.token
                        if (token != null) {
                            Log.d("AuthenticationManager", "Token: $token")
                            trySend(AuthResponse.TokenSuccess(token))
                        } else {
                            Log.d("AuthenticationManager", "Token no error 1")
                            trySend(AuthResponse.Error("Token is null"))
                        }
                    } else {
                        Log.d("AuthenticationManager", "Token no error 2")
                        trySend(AuthResponse.Error(task.exception?.message ?: "Failed to get token"))
                    }
                    close()
                }
        } else {
            Log.d("Authetnit", "no user")
            trySend(AuthResponse.Error("No user logged in"))
            close()
        }
        awaitClose()
    }

    sealed interface AuthResponse {
        data object Success : AuthResponse
        data class TokenSuccess(val token: String) : AuthResponse
        data class Error(val message: String) : AuthResponse
    }

}


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authManager: AuthenticationManager
) : ViewModel() {

    private val _tokenState = MutableStateFlow<String?>(null)
    val tokenState = _tokenState.asStateFlow()

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState = _errorState.asStateFlow()

    // Function to handle email login and get token
    fun loginWithEmail(email: String, password: String) {
        viewModelScope.launch {
            authManager.loginWithEmail(email, password).collect { response ->
                handleAuthResponse(response)
            }
        }
    }

    // Function to handle Google sign in and get token
    fun signInWithGoogle() {
        viewModelScope.launch {
            authManager.signInWithGoogle().collect { response ->
                handleAuthResponse(response)
            }
        }
    }

    private fun handleAuthResponse(response: AuthenticationManager.AuthResponse) {
        when (response) {
            is AuthenticationManager.AuthResponse.TokenSuccess -> {
                _tokenState.value = response.token
                _errorState.value = null
                Log.d("AuthViewModel", "Token received: ${response.token}")
            }
            is AuthenticationManager.AuthResponse.Error -> {
                _errorState.value = response.message
                _tokenState.value = null
                Log.e("AuthViewModel", "Auth error: ${response.message}")
            }
            else -> {
                _errorState.value = "Unexpected response"
                _tokenState.value = null
                Log.e("AuthViewModel", "Unexpected auth response")
            }
        }
    }
}

// Module for Hilt DI
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAuthenticationManager(
        @ApplicationContext context: Context
    ): AuthenticationManager {
        return AuthenticationManager(context)
    }
}