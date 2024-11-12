package com.example.lechendasapp

import com.example.lechendasapp.data.model.Climate
import com.example.lechendasapp.data.repository.ClimateRepository
import com.example.lechendasapp.viewmodels.ClimateUiState
import com.example.lechendasapp.viewmodels.ClimateViewModel
import com.example.lechendasapp.viewmodels.toClimateLog
import com.example.lechendasapp.viewmodels.toClimateUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class ClimateViewModelTest {

    private lateinit var testRepository: TestClimateRepository
    private lateinit var viewModel: ClimateViewModel

    @Before
    fun setup() {
        testRepository = TestClimateRepository()
        viewModel = ClimateViewModel(testRepository)
    }

    @Test
    fun testInitialClimateUiState() {
        val expected = ClimateUiState(
            rainfall = "",
            maxTemp = "",
            minTemp = "",
            maxHumidity = "",
            minHumidity = "",
            ravineLevel = "",
            observations = ""
        )

        assertEquals(expected, viewModel.climateUiState.value)
    }

    @Test
    fun testInitialMonitorLogId() {
        assertEquals(0L, viewModel.monitorLogId.value)
    }

    @Test
    fun testUpdateUiState() {
        // Given
        val newState = ClimateUiState(
            rainfall = "100",
            maxTemp = "30",
            minTemp = "20",
            maxHumidity = "90",
            minHumidity = "40",
            ravineLevel = "5",
            observations = "Test observation"
        )

        // When
        viewModel.updateUiState(newState)

        // Then
        assertEquals(newState, viewModel.climateUiState.value)
    }

    @Test
    fun testSetMonitorLogId() {
        // Given
        val testId = 123L

        // When
        viewModel.setMonitorLogId(testId)

        // Then
        assertEquals(testId, viewModel.monitorLogId.value)
    }

    @Test
    fun testAddNewLog() {
        // Given
        val testUiState = ClimateUiState(
            rainfall = "100",
            maxTemp = "30",
            minTemp = "20",
            maxHumidity = "90",
            minHumidity = "40",
            ravineLevel = "5",
            observations = "Test observation"
        )
        val testMonitorLogId = 123L

        // When
        viewModel.updateUiState(testUiState)
        viewModel.setMonitorLogId(testMonitorLogId)
        viewModel.addNewLog()

        // Then
        val expectedClimate = Climate(
            id = 0,
            monitorLogId = testMonitorLogId,
            rainfall = 100,
            maxTemp = 30,
            minTemp = 20,
            maxHumidity = 90,
            minHumidity = 40,
            ravineLevel = 5,
            observations = "Test observation"
        )

        assertEquals(expectedClimate, testRepository.lastInsertedClimate)
    }

    @Test
    fun testClimateToUiStateConversion() {
        // Given
        val climate = Climate(
            id = 1,
            monitorLogId = 123,
            rainfall = 100,
            maxTemp = 30,
            minTemp = 20,
            maxHumidity = 90,
            minHumidity = 40,
            ravineLevel = 5,
            observations = "Test"
        )

        // When
        val uiState = climate.toClimateUiState()

        // Then
        assertEquals("100", uiState.rainfall)
        assertEquals("30", uiState.maxTemp)
        assertEquals("20", uiState.minTemp)
        assertEquals("90", uiState.maxHumidity)
        assertEquals("40", uiState.minHumidity)
        assertEquals("5", uiState.ravineLevel)
    }

    @Test
    fun testUiStateToClimateConversion() {
        // Given
        val uiState = ClimateUiState(
            rainfall = "100",
            maxTemp = "30",
            minTemp = "20",
            maxHumidity = "90",
            minHumidity = "40",
            ravineLevel = "5",
            observations = "Test"
        )

        // When
        val climate = uiState.toClimateLog()

        // Then
        assertEquals(100, climate.rainfall)
        assertEquals(30, climate.maxTemp)
        assertEquals(20, climate.minTemp)
        assertEquals(90, climate.maxHumidity)
        assertEquals(40, climate.minHumidity)
        assertEquals(5, climate.ravineLevel)
        assertEquals("Test", climate.observations)
    }
}

// Simple Test Repository Implementation
class TestClimateRepository : ClimateRepository {
    var lastInsertedClimate: Climate? = null
    private val climateList = mutableListOf<Climate>()

    override fun getClimateStream(): Flow<List<Climate>> = flowOf(climateList)

    override fun getIndividualClimateStream(climateId: Long): Flow<Climate> {
        return flowOf(climateList.first { it.id == climateId })
    }

    override suspend fun getClimate(): List<Climate> = climateList

    override suspend fun getClimateById(climateId: Long): Climate? {
        return climateList.find { it.id == climateId }
    }

    override suspend fun insertClimate(climate: Climate) {
        lastInsertedClimate = climate
        climateList.add(climate)
    }

    override suspend fun updateClimate(climate: Climate) {
        val index = climateList.indexOfFirst { it.id == climate.id }
        if (index != -1) {
            climateList[index] = climate
        }
    }

    override suspend fun deleteClimate(climate: Climate) {
        climateList.removeIf { it.id == climate.id }
    }
}