package com.example.lechendasapp

import com.example.lechendasapp.data.model.Trap
import com.example.lechendasapp.data.repository.TrapRepository
import com.example.lechendasapp.fakes.FakeTrapRepository
import com.example.lechendasapp.screens.CheckList
import com.example.lechendasapp.viewmodels.TrapUiState
import com.example.lechendasapp.viewmodels.TrapViewModel
import com.example.lechendasapp.viewmodels.toTrapUiState
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TrapViewModelTest {

    private lateinit var trapRepository: TrapRepository
    private lateinit var viewModel: TrapViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        trapRepository = FakeTrapRepository() // Use the fake repository
        viewModel = TrapViewModel(trapRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `updateUiState should update the state`() {
        // Given
        val newUiState = TrapUiState(code = "Code123", cameraName = "Camera1")

        // When
        viewModel.updateUiState(newUiState)

        // Then
        assertEquals(newUiState, viewModel.trapUiState.value)
    }

    @Test
    fun `setMonitorLogId should update the monitorLogId`() {
        // When
        viewModel.setMonitorLogId(123L)

        // Then
        assertEquals(123L, viewModel.monitorLogId.value)
    }

    @Test
    fun `setTrapId should fetch trap and update ui state`() = runTest {
        // Given
        val trap = Trap(
            id = 1,
            monitorLogId = 123,
            code = "Trap123",
            cameraName = "Camera123",
            cameraPlate = "Plate123",
            guayaPlate = "Guaya123",
            roadWidth = 10,
            installationDate = "2024-11-11",
            lensHeight = 5,
            objectiveDistance = 20,
            checkList = "PROGRAMMED,INSTALLED",
            observations = "Test observation"
        )
        trapRepository.insertTrap(trap)

        // When
        viewModel.setTrapId(1L)
        advanceUntilIdle()

        // Then
        val expectedUiState = trap.toTrapUiState()
        assertEquals(expectedUiState, viewModel.trapUiState.value)
    }

    @Test
    fun `addNewLog should insert new trap when logId is 0`() = runTest {
        // Given
        val uiState = TrapUiState(code = "Code123", cameraName = "Camera1")
        viewModel.updateUiState(uiState)
        viewModel.setMonitorLogId(100L)

        // When
        viewModel.addNewLog()
        advanceUntilIdle()

        // Then
        val traps = (trapRepository as FakeTrapRepository).traps
        assertTrue(traps.any { it.code == "Code123" && it.monitorLogId == 100L })
    }

    @Test
    fun `addNewLog should update trap when logId is not 0`() = runTest {
        // Given
        val trap = Trap(
            id = 10,
            monitorLogId = 123,
            code = "Trap123",
            cameraName = "Camera123",
            cameraPlate = "Plate123",
            guayaPlate = "Guaya123",
            roadWidth = 10,
            installationDate = "2024-11-11",
            lensHeight = 5,
            objectiveDistance = 20,
            checkList = "PROGRAMMED",
            observations = null
        )
        trapRepository.insertTrap(trap)

        val uiState = trap.toTrapUiState().copy(cameraName = "UpdatedCamera")
        viewModel.updateUiState(uiState)
        viewModel.setTrapId(10L)

        // When
        viewModel.addNewLog()
        advanceUntilIdle()

        // Then
        val updatedTrap = (trapRepository as FakeTrapRepository).traps.firstOrNull { it.id == 10L }
        assertNotNull(updatedTrap)
        assertEquals("UpdatedCamera", updatedTrap?.cameraName)
    }

    @Test
    fun `updateCheckList should update the checklist correctly`() {
        // Given
        val initialCheckList = CheckList.PROGRAMMED to false
        viewModel.updateUiState(TrapUiState(checkList = mapOf(initialCheckList)))

        // When
        viewModel.updateCheckList(CheckList.PROGRAMMED, true)

        // Then
        val updatedCheckList = viewModel.trapUiState.value.checkList
        assertEquals(true, updatedCheckList[CheckList.PROGRAMMED])
    }
}

