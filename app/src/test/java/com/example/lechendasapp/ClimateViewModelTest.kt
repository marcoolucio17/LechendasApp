package com.example.lechendasapp

import com.example.lechendasapp.data.model.Climate
import com.example.lechendasapp.fakes.FakeClimateRepository
import com.example.lechendasapp.viewmodels.ClimateUiState
import com.example.lechendasapp.viewmodels.ClimateViewModel
import com.example.lechendasapp.viewmodels.toClimateUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ClimateViewModelTest {

    private lateinit var climateRepository: FakeClimateRepository
    private lateinit var climateViewModel: ClimateViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        climateRepository = FakeClimateRepository()
        climateViewModel = ClimateViewModel(climateRepository)
    }

    @After
    fun tearDown() {
       Dispatchers.resetMain()
    }

    @Test
    fun `initial state is empty` () {
        val uiState = climateViewModel.climateUiState.value
        assertTrue(uiState == ClimateUiState.empty())
    }

    @Test
    fun `setMonitorLogId updates the monitorLogId` () {
        climateViewModel.setMonitorLogId(123L)
        assertEquals(123L, climateViewModel.monitorLogId.value)
    }

    @Test
    fun `setClimateId fetches climate and updates UiState `() = runTest {
        val mockClimate = Climate(
            id = 1,
            monitorLogId = 123L,
            rainfall = 100,
            maxTemp = 35,
            minTemp = 20,
            maxHumidity = 90,
            minHumidity = 50,
            ravineLevel = 10,
            observations = "Test observations"
        )
        climateRepository.insertClimate(mockClimate)
        climateViewModel.setClimateId(1L)
        advanceUntilIdle()

        val uiState = climateViewModel.climateUiState.value
        assertEquals(mockClimate.toClimateUiState(), uiState)
    }

    @Test
    fun `addNewLog inserts new climate when id is 0`() = runTest {
        climateViewModel.setMonitorLogId(123L)
        climateViewModel.updateUiState(
            ClimateUiState(
                rainfall = "100",
                maxTemp = "35",
                minTemp = "20",
                maxHumidity = "90",
                minHumidity = "50",
                ravineLevel = "10",
                observations = "New log"
            )
        )

        climateViewModel.addNewLog()
        advanceUntilIdle()

        val climates = climateRepository.getClimate()


        assertEquals(1, climates.size)
        val insertedClimate = climates.first()
        assertEquals(123L, insertedClimate.monitorLogId)
        assertEquals(100, insertedClimate.rainfall)
        assertEquals("New log", insertedClimate.observations)
    }

    @Test
    fun `addNewLog updates climate when id is not 0` () = runTest {
        val existingClimate = Climate(
            id = 1,
            monitorLogId = 123L,
            rainfall = 100,
            maxTemp = 35,
            minTemp = 20,
            maxHumidity = 90,
            minHumidity = 50,
            ravineLevel = 10,
            observations = "Old log"
        )
        climateRepository.insertClimate(existingClimate)

        climateViewModel.setMonitorLogId(123L)
        climateViewModel.setClimateId(1L)
        climateViewModel.updateUiState(
            ClimateUiState(
                rainfall = "200",
                maxTemp = "40",
                minTemp = "25",
                maxHumidity = "95",
                minHumidity = "55",
                ravineLevel = "15",
                observations = "Updated log"
            )
        )

        climateViewModel.addNewLog()
        advanceUntilIdle()

        val updatedClimate = climateRepository.getClimateById(1L)
        assertNotNull(updatedClimate)
        assertEquals(200, updatedClimate?.rainfall)
        assertEquals("Updated log", updatedClimate?.observations)
    }

    @Test
    fun `resetForm clears UiState` () {
        climateViewModel.updateUiState(
            ClimateUiState(
                rainfall = "100",
                maxTemp = "35",
                minTemp = "20",
                maxHumidity = "90",
                minHumidity = "50",
                ravineLevel = "10",
                observations = "Test observations"
            )
        )
        climateViewModel.resetForm()

        val uiState = climateViewModel.climateUiState.value
        assertTrue(uiState == ClimateUiState.empty())
    }

    @Test
    fun `validateFields sets errors when fields are empty` () {
        climateViewModel.updateUiState(ClimateUiState())
        val isValid = climateViewModel.validateFields()

        assertFalse(isValid)
        val errors = climateViewModel.climateUiState.value.errors
        assertEquals(6, errors.size)
        assertTrue(errors.containsKey("rainfall"))
        assertEquals("Este campo es obligatorio.", errors["rainfall"])
    }

    @Test
    fun `validateFields returns true when fields are valid `() {
        climateViewModel.updateUiState(
            ClimateUiState(
                rainfall = "100",
                maxTemp = "35",
                minTemp = "20",
                maxHumidity = "90",
                minHumidity = "50",
                ravineLevel = "10"
            )
        )

        val isValid = climateViewModel.validateFields()
        assertTrue(isValid)
        assertTrue(climateViewModel.climateUiState.value.errors.isEmpty())
    }
}