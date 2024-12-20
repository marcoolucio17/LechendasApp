package com.example.lechendasapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.lechendasapp.data.model.MonitorLog
import com.example.lechendasapp.screens.SearchContent
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SearchScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLogList() {
        val mockLogList = List(10) { MonitorLog(id = it.toLong(), logType = "Type $it", dateMillis = 123456789, gpsCoordinates = "12.34, 56.78", climateType = "Tropical", seasons = "Summer", zone = "Zone 1", userId = "123", location = "") }

        composeTestRule.setContent {
            SearchContent(
                logList = mockLogList,
                onTransectClick = {},
                onClimateClick = {},
                onCoverageClick = {},
                onTrapClick = {},
                onVegetationClick = {},
                onDelete = {}
            )
        }

        // Verify that the first log item is displayed
        composeTestRule.onNodeWithText("Type 0").assertIsDisplayed()

        // Perform a click on the first log item
        composeTestRule.onNodeWithText("Type 0").performClick()

        // Verify that the log details are shown (optional)
        composeTestRule.onNodeWithText("GPS Lat").assertIsDisplayed()
        composeTestRule.onNodeWithText("12.34").assertIsDisplayed()
    }
}
