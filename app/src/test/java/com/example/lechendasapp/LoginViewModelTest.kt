package com.example.lechendasapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.lechendasapp.data.model.User
import com.example.lechendasapp.data.repository.UserRepository
import com.example.lechendasapp.viewmodels.LoginViewModel
import com.example.lechendasapp.viewmodels.LoginUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var userRepository: UserRepository
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher) // Set Main dispatcher for testing
        userRepository = mock()
        viewModel = LoginViewModel(userRepository)
    }


    @Test
    fun `checkUserCredentials with invalid credentials should not be able to log in`() = runTest(testDispatcher) {
        // Creamos un item de un usuario inválido
        val user = User(
            email = "valid@example.com",
            password = "wrongPassword",
            birthDate = "2000-01-01",
            country = "USA",
            firstName = "Test",
            lastName = "User",
            height = "180",
            id = 1,
            role = "user",
            team = 100
        )

        // Intentamos hacer login utilizando estas credenciales
        whenever(userRepository.getUserByEmail("valid@example.com")).thenReturn(user)
        viewModel.updateUiState(LoginUiState(email = "valid@example.com", password = "correctPassword"))

        // Checamos si pudo realizar login o no
        var isLoginSuccess = true
        viewModel.checkUserCredentials { isLoginSuccess = false }
        testDispatcher.scheduler.advanceUntilIdle()

        // Mandamos a llamar el assert
        assertTrue(isLoginSuccess)
    }
}

