package com.example.lechendasapp

import androidx.compose.ui.test.*
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.auth0.android.Auth0
import com.example.lechendasapp.screens.LoginScreen
import com.example.lechendasapp.viewmodels.LoginViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    val fakeViewModel = LoginViewModel(
        auth0 = FakeAuth0.getInstance()
    )

    @Test
    fun testLoginScreen_initialState() {
        // Set the content of the screen
        composeTestRule.setContent {
            LoginScreen(
                onBack = {},
                onLoginSuccess = {},
                viewModel = fakeViewModel
            )
        }

        // Verify that the email and password fields are displayed
        composeTestRule.onNodeWithText("Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Contraseña").assertIsDisplayed()

        // Verify that the login button is displayed
        composeTestRule.onNodeWithText("Entrar").assertIsDisplayed()
    }

    @Test
    fun testLoginScreen_emailInput() {
        // Set the content of the screen
        composeTestRule.setContent {
            LoginScreen(
                onBack = {},
                onLoginSuccess = {},
                viewModel = fakeViewModel
            )
        }

        // Type an email into the email input field
        val email = "test@example.com"
        composeTestRule.onNodeWithText("Email").performTextInput(email)

        // Verify that the entered email is displayed
        composeTestRule.onNodeWithText(email).assertIsDisplayed()
    }

    @Test
    fun testLoginScreen_passwordInput() {
        // Set the content of the screen
        composeTestRule.setContent {
            LoginScreen(
                onBack = {},
                onLoginSuccess = {},
                viewModel = fakeViewModel
            )
        }

        // Type a password into the password input field
        val password = "password123"
        composeTestRule.onNodeWithText("Contraseña").performTextInput(password)

        // Verify that the entered password is displayed (note: it might not display due to password transformation)
        composeTestRule.onNodeWithText("password123").assertDoesNotExist() // As expected, password will be transformed
    }

    @Test
    fun testLoginScreen_passwordVisibilityToggle() {
        // Set the content of the screen
        composeTestRule.setContent {
            LoginScreen(
                onBack = {},
                onLoginSuccess = {},
                viewModel = fakeViewModel
            )
        }

        // input the password
        val password = "password123"
        composeTestRule.onNodeWithText("Contraseña").performTextInput(password)

        //verify password not visible
        composeTestRule.onNodeWithText("password123").assertIsNotDisplayed()

        // Verify that the password visibility toggle icon is displayed
        composeTestRule.onNodeWithContentDescription("Ver contraseña").assertIsDisplayed()

        // Click the password visibility toggle
        composeTestRule.onNodeWithContentDescription("Ver contraseña").performClick()

        // Verify that the password visibility has been toggled (password becomes visible)
        composeTestRule.onNodeWithText("password123").assertIsDisplayed()
    }
}


object FakeAuth0 {
    fun getInstance(): Auth0 {
        return Auth0.getInstance(
            clientId = "fake-client-id",
            domain = "https://fake-domain.auth0.com"
        )
    }
}


