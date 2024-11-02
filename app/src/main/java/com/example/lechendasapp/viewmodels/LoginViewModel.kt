package com.example.lechendasapp.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lechendasapp.data.model.User
import com.example.lechendasapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    var userCredentials: UserCredentials = UserCredentials(),
)

data class UserCredentials(
    val email: String = "",
    val password: String = "",
)

fun User.toLoginUiState(): LoginUiState = LoginUiState(
    userCredentials = this.toUserDetails()
)

fun User.toUserDetails(): UserCredentials = UserCredentials(
    email = this.email,
    password = this.password,
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _loginUiState = mutableStateOf(LoginUiState())
    val loginUiState: State<LoginUiState> = _loginUiState

    private val _isLoggedIn = mutableStateOf(false)
    private val isLoggedIn: State<Boolean> = _isLoggedIn

    fun checkUserCredentials(onLoginSuccess: () -> Unit) {
        viewModelScope.launch {
            if (checkUser()) {
                _isLoggedIn.value = true
                onLoginSuccess()
                Log.d("LoginViewModel", "checkUserCredentials: $isLoggedIn")
            } else {
                _isLoggedIn.value = false
                Log.d("LoginViewModel", "checkUserCredentials: $isLoggedIn")
            }
        }
    }

    //check password and email in room database
    private suspend fun checkUser(): Boolean {
        //validate input
        if (!validateInput()) {
            return false
        }

        //validate user to database
        val testUser: User? = userRepository.getUserByEmail(_loginUiState.value.userCredentials.email)
        if (testUser == null) {
            return false
        } else if (testUser.password != loginUiState.value.userCredentials.password) {
            return false
        }

        //if email and password is correct, return true and user is logged in
        return true
    }

    //auxiliar: check if email and password is not empty
    private fun validateInput(): Boolean {
        return _loginUiState.value.userCredentials.email.isNotEmpty() && _loginUiState.value.userCredentials.password.isNotEmpty()
    }

    //get email and password from the users input
    fun updateUiState(newUserDetail: UserCredentials) {
        _loginUiState.value = _loginUiState.value.copy(userCredentials = newUserDetail)
        Log.d("LoginViewModel", "updateUiState: $newUserDetail")
    }
}