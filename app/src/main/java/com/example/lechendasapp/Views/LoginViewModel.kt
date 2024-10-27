package com.example.lechendasapp.views

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lechendasapp.data.repository.UserRepository
import com.example.lechendasapp.data.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val userDetail: UserDetails = UserDetails(),
)

data class UserDetails(
    val id: Long? = null,
    val firstName: String = "Polonia",
    val lastName: String = "Herzegovina",
    val email: String = "",
    val password: String = "",
    val country: String = "México",
    val occupation: String? = null,
    val birthDate: String = "Hoy",
)

fun UserDetails.toUser(): User  = User (
    id = id,
    firstName = firstName,
    lastName = lastName,
    email = email,
    password = password,
    country = country,
    occupation = occupation,
    birthDate = birthDate
)

fun User.toLoginUiState(): LoginUiState = LoginUiState(
    userDetail = this.toUserDetails()
)

fun User.toUserDetails(): UserDetails = UserDetails(
    id = this.id,
    firstName = this.firstName,
    lastName = this.lastName,
    email = this.email,
    password = this.password,
    occupation = this.occupation,
    country = this.country,
    birthDate = this.birthDate
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    var loginUiState by mutableStateOf(LoginUiState())
        private set

    // Insert user to room database
    fun insertUser() {
        viewModelScope.launch {
            userRepository.insertUser(loginUiState.userDetail.toUser())
            Log.d("LoginViewModel", "insertUser: ${loginUiState.userDetail.toUser()}")
        }
    }

    fun updateUiState(newUserDetail: UserDetails) {
        loginUiState = LoginUiState(newUserDetail)
        Log.d("LoginViewModel", "updateUiState: $newUserDetail")
    }

    fun updateEmail(newEmail: String) {
        loginUiState = loginUiState.copy(
            userDetail = loginUiState.userDetail.copy(email = newEmail)
        )
    }

    fun updatePassword(newPassword: String) {
        loginUiState = loginUiState.copy(
            userDetail = loginUiState.userDetail.copy(password = newPassword)
        )
    }
}