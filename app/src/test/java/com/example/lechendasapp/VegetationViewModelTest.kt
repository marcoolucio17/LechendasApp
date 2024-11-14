package com.example.lechendasapp

import com.example.lechendasapp.data.model.Vegetation
import com.example.lechendasapp.fakes.FakeVegetationRepository
import com.example.lechendasapp.viewmodels.VegetationUiState
import com.example.lechendasapp.viewmodels.VegetationViewModel
import com.example.lechendasapp.viewmodels.toVegetation
import com.example.lechendasapp.viewmodels.toVegetationUiState
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

class VegetationViewModelTest {

    private lateinit var fakeRepository: FakeVegetationRepository
    private lateinit var viewModel: VegetationViewModel

    @Before
    fun setup() {
        fakeRepository = FakeVegetationRepository()
        viewModel = VegetationViewModel(fakeRepository)
    }

    @Test
    fun testInitialVegetationUiState() {
        val expected = VegetationUiState()
        assertEquals(expected, viewModel.vegetationUiState.value)
    }

    @Test
    fun testInitialMonitorLogId() {
        assertEquals(0L, viewModel.monitorLogId.value)
    }

    @Test
    fun testUpdateUiState() {
        val newState = VegetationUiState(
            code = "001",
            quadrant = "A",
            subQuadrant = "1",
            growthHabit = "Tree",
            commonName = "Oak",
            scientificName = "Quercus",
            plate = "P001",
            circumference = "100",
            distance = "5",
            height = "20",
            observations = "Healthy tree"
        )
        viewModel.updateUiState(newState)
        assertEquals(newState, viewModel.vegetationUiState.value)
    }

    @Test
    fun testSetMonitorLogId() {
        val testId = 123L
        viewModel.setMonitorLogId(testId)
        assertEquals(testId, viewModel.monitorLogId.value)
    }

    @Test
    fun testAddNewLog() {
        val testUiState = VegetationUiState(
            code = "001",
            quadrant = "A",
            quadrantSecond = "1",
            subQuadrant = "1",
            growthHabit = "Tree",
            commonName = "Oak",
            scientificName = "Quercus",
            plate = "P001",
            circumference = "100",
            distance = "5",
            height = "20",
            observations = "Healthy tree"
        )
        val testMonitorLogId = 123L

        viewModel.updateUiState(testUiState)
        viewModel.setMonitorLogId(testMonitorLogId)
        viewModel.addNewLog()

        val expectedVegetation = Vegetation(
            id = 0,
            monitorLogId = testMonitorLogId,
            code = "001",
            quadrant = "A1", // Combines quadrant and quadrantSecond
            subQuadrant = "1",
            growthHabit = "Tree",
            commonName = "Oak",
            scientificName = "Quercus",
            plate = "P001",
            circumference = 100,
            distance = 5,
            height = 20,
            observations = "Healthy tree"
        )

        assertEquals(expectedVegetation, fakeRepository.lastInsertedVegetation)
    }

    @Test
    fun testVegetationToUiStateConversion() {
        val vegetation = Vegetation(
            id = 1,
            monitorLogId = 123,
            code = "001",
            quadrant = "A1",
            subQuadrant = "1",
            growthHabit = "Tree",
            commonName = "Oak",
            scientificName = "Quercus",
            plate = "P001",
            circumference = 100,
            distance = 5,
            height = 20,
            observations = "Healthy tree"
        )
        val uiState = vegetation.toVegetationUiState()

        assertEquals("001", uiState.code)
        assertEquals("A", uiState.quadrant)
        assertEquals("1", uiState.subQuadrant)
        assertEquals("Tree", uiState.growthHabit)
        assertEquals("Oak", uiState.commonName)
        assertEquals("Quercus", uiState.scientificName)
        assertEquals("P001", uiState.plate)
        assertEquals("100", uiState.circumference)
        assertEquals("5", uiState.distance)
        assertEquals("20", uiState.height)
        assertEquals("Healthy tree", uiState.observations)
    }

    @Test
    fun testUiStateToVegetationConversion() {
        val uiState = VegetationUiState(
            code = "001",
            quadrant = "A",
            quadrantSecond = "1",
            subQuadrant = "1",
            growthHabit = "Tree",
            commonName = "Oak",
            scientificName = "Quercus",
            plate = "P001",
            circumference = "100",
            distance = "5",
            height = "20",
            observations = "Healthy tree"
        )
        val vegetation = uiState.toVegetation()

        assertEquals("001", vegetation)
    }
}