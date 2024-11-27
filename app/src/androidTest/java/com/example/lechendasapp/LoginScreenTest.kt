package com.example.lechendasapp

import android.app.Application
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.foundation.layout.*
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.lechendasapp.screens.LoginScreen
import com.example.lechendasapp.screens.LoginBody
import com.example.lechendasapp.screens.rememberPreviewLoginViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLoginScreen_initialState() {
        // Set the content of the screen
        composeTestRule.setContent {
            LoginScreen(
                onBack = {},
                onLoginSuccess = {},
                viewModel = rememberPreviewLoginViewModel()
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
                viewModel = rememberPreviewLoginViewModel()
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
                viewModel = rememberPreviewLoginViewModel()
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
                viewModel = rememberPreviewLoginViewModel()
            )
        }

        // Verify that the password visibility toggle icon is displayed
        composeTestRule.onNodeWithContentDescription("Ver contraseña").assertIsDisplayed()

        // Click the password visibility toggle
        composeTestRule.onNodeWithContentDescription("Ver contraseña").performClick()

        // Verify that the password visibility has been toggled (password becomes visible)
        composeTestRule.onNodeWithText("password123").assertIsDisplayed()
    }

    @Test
    fun testLoginScreen_buttonClick() {
        var wasLoginSuccessful = false

        // Set the content of the screen
        composeTestRule.setContent {
            LoginScreen(
                onBack = {},
                onLoginSuccess = { wasLoginSuccessful = true },
                viewModel = rememberPreviewLoginViewModel()
            )
        }

        // Simulate clicking the login button
        composeTestRule.onNodeWithText("Entrar").performClick()

        // Verify that the login was successful
        assert(wasLoginSuccessful)
    }
}
