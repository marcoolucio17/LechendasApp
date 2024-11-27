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
import com.example.lechendasapp.screens.HomeScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testHomeScreen_initialState() {
        // Set the content of the screen
        composeTestRule.setContent {
            HomeScreen(
                onBack = {},
                currentRoute = "home",
                onMenuClick = {},
                onSearchClick = {},
                onSettingsClick = {},
                onAddClick = {}
            )
        }

        // Verify that the "Bienvenido" text is displayed in the center of the screen
        composeTestRule.onNodeWithText("Bienvenido").assertIsDisplayed()

        // Verify that the add button (icon) is present
        composeTestRule.onNodeWithContentDescription("Add").assertIsDisplayed()
    }

    @Test
    fun testHomeScreen_addButtonClick() {
        var isAddClicked = false

        // Set the content of the screen
        composeTestRule.setContent {
            HomeScreen(
                onBack = {},
                currentRoute = "home",
                onMenuClick = {},
                onSearchClick = {},
                onSettingsClick = {},
                onAddClick = { isAddClicked = true }
            )
        }

        // Perform click on the add button
        composeTestRule.onNodeWithContentDescription("Add").performClick()

        // Verify if the add button click worked
        assert(isAddClicked)
    }

    @Test
    fun testHomeScreen_bottomNavBar() {
        // Set the content of the screen
        composeTestRule.setContent {
            HomeScreen(
                onBack = {},
                currentRoute = "home",
                onMenuClick = {},
                onSearchClick = {},
                onSettingsClick = {},
                onAddClick = {}
            )
        }

        // Verify that the bottom navigation items are displayed
        composeTestRule.onNodeWithText("Home").assertIsDisplayed()
        composeTestRule.onNodeWithText("Search").assertIsDisplayed()
        composeTestRule.onNodeWithText("Settings").assertIsDisplayed()

        // Optionally: you can click on one of the navigation items and check if it triggers the appropriate action
        composeTestRule.onNodeWithText("Home").performClick()
    }

    @Test
    fun testHomeScreen_contentDisplayed() {
        // Set the content of the screen
        composeTestRule.setContent {
            HomeScreen(
                onBack = {},
                currentRoute = "home",
                onMenuClick = {},
                onSearchClick = {},
                onSettingsClick = {},
                onAddClick = {}
            )
        }

        // Verify that the content (chart or other elements) is visible
        composeTestRule.onNodeWithTag("AnimatedCircleChartScreen").assertIsDisplayed()
    }
}
