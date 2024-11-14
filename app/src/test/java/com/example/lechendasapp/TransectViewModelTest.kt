package com.example.lechendasapp

import com.example.lechendasapp.data.model.Animal
import com.example.lechendasapp.fakes.FakeTransectRepository
import com.example.lechendasapp.viewmodels.AnimalUiSate
import com.example.lechendasapp.viewmodels.AnimalViewModel
import com.example.lechendasapp.viewmodels.toAnimal
import com.example.lechendasapp.viewmodels.toAnimalUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

@OptIn(ExperimentalCoroutinesApi::class)
class TransectViewModelTest {

    private lateinit var fakeRepository: FakeTransectRepository
    private lateinit var viewModel: AnimalViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeRepository = FakeTransectRepository()
        viewModel = AnimalViewModel(fakeRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testInitialAnimalUiState() {
        val expected = AnimalUiSate(
            animalType = "",
            commonName = "",
            scientificName = "",
            quantity = "",
            observationType = "",
            transectName = "",
            observationHeight = "",
            observations = ""
        )

        assertEquals(expected, viewModel.animalUiState.value)
    }

    @Test
    fun testInitialMonitorLogId() {
        assertEquals(0L, viewModel.monitorLogId.value)
    }

    @Test
    fun testUpdateUiState() {
        // Given
        val newState = AnimalUiSate(
            animalType = "Mammal",
            commonName = "Elephant",
            scientificName = "Loxodonta",
            quantity = "2",
            observationType = "Visual",
            transectName = "Savanna",
            observationHeight = "2.5",
            observations = "Observed near water"
        )

        // When
        viewModel.updateUiState(newState)

        // Then
        assertEquals(newState, viewModel.animalUiState.value)
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
    fun testSaveAnimal() = runTest {
        // Given
        val testUiState = AnimalUiSate(
            animalType = "Mammal",
            commonName = "Bear",
            scientificName = "Ursus arctos",
            quantity = "2",
            observationType = "Sight",
            transectName = "Forest",
            observationHeight = "5",
            observations = "Near river"
        )
        val testMonitorLogId = 123L

        // When
        viewModel.updateUiState(testUiState)
        viewModel.setMonitorLogId(testMonitorLogId)
        viewModel.saveAnimal()

        advanceUntilIdle()

        // Then
        val expectedAnimal = Animal(
            id = 0,
            monitorLogId = testMonitorLogId,
            animalType = "Mammal",
            commonName = "Bear",
            scientificName = "Ursus arctos",
            quantity = 2,
            observationType = "Sight",
            transectName = "Forest",
            observationHeight = "5",
            observations = "Near river"
        )

        // Verifica que el animal guardado sea igual al esperado
        assertEquals(expectedAnimal, fakeRepository.lastInsertedAnimal)
    }

    @Test
    fun testAnimalToUiStateConversion() {
        // Given
        val animal = Animal(
            id = 1,
            monitorLogId = 123,
            animalType = "Reptile",
            commonName = "Iguana",
            scientificName = "Iguana iguana",
            quantity = 3,
            observationType = "Visual",
            transectName = "Jungle",
            observationHeight = "1.0",
            observations = "Near rocks"
        )

        // When
        val uiState = animal.toAnimalUiState()

        // Then
        assertEquals("Reptile", uiState.animalType)
        assertEquals("Iguana", uiState.commonName)
        assertEquals("Iguana iguana", uiState.scientificName)
        assertEquals("3", uiState.quantity)
        assertEquals("Visual", uiState.observationType)
        assertEquals("Jungle", uiState.transectName)
        assertEquals("1.0", uiState.observationHeight)
        assertEquals("Near rocks", uiState.observations)
    }

    @Test
    fun testUiStateToAnimalConversion() {
        // Given
        val uiState = AnimalUiSate(
            animalType = "Fish",
            commonName = "Salmon",
            scientificName = "Salmo salar",
            quantity = "12",
            observationType = "Visual",
            transectName = "River",
            observationHeight = "0.5",
            observations = "Spawning season"
        )

        // When
        val animal = uiState.toAnimal()

        // Then
        assertEquals("Fish", animal.animalType)
        assertEquals("Salmon", animal.commonName)
        assertEquals("Salmo salar", animal.scientificName)
        assertEquals(12, animal.quantity)
        assertEquals("Visual", animal.observationType)
        assertEquals("River", animal.transectName)
        assertEquals("0.5", animal.observationHeight)
        assertEquals("Spawning season", animal.observations)
    }
}
