package com.example.lechendasapp


import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.lechendasapp.screens.ConfigurationScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ConfigurationScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testConfigurationScreen_logoutDialog() {
        // Set the content of the screen
        composeTestRule.setContent {
            ConfigurationScreen(
                onBack = {},
                currentRoute = "settings",
                onMenuClick = {},
                onSearchClick = {},
                onSettingsClick = {},
                onEditProfile = {},
                onLogoutConfirmed = { /* Handle logout */ }
            )
        }

        // Verify that the "Cerrar sesión" option is visible
        composeTestRule.onNodeWithText("Cerrar sesión").assertIsDisplayed()

        // Click on "Cerrar sesión" to show the logout dialog
        composeTestRule.onNodeWithText("Cerrar sesión").performClick()
    }

    @Test
    fun testConfigurationScreen_contactSection() {
        // Set the content of the screen
        composeTestRule.setContent {
            ConfigurationScreen(
                onBack = {},
                currentRoute = "settings",
                onMenuClick = {},
                onSearchClick = {},
                onSettingsClick = {},
                onEditProfile = {},
                onLogoutConfirmed = { /* Handle logout */ }
            )
        }

        // Verify that the "CONTACTO" section is visible
        composeTestRule.onNodeWithText("CONTACTO").assertIsDisplayed()

        // Verify that the contact information is displayed
        val contactText = "Correo: lechendas@tec.mx\n\n" +
                "Equipo desarrollador:\n\n" +
                "Daniel Molina\n" +
                "Marco Lucio\n" +
                "Kaled Enríquez\n" +
                "Isaac Enríquez\n" +
                "Andrés Quintanar\n"
        composeTestRule.onNodeWithText(contactText).assertIsDisplayed()
    }
}
