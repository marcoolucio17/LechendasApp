package com.example.lechendasapp

import com.example.lechendasapp.data.model.Coverage
import com.example.lechendasapp.FakeCoverageRepository
import com.example.lechendasapp.viewmodels.SearchCoverageUiState
import com.example.lechendasapp.viewmodels.SearchCoverageViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SearchCoverageViewModelTest {

    private lateinit var viewModel: SearchCoverageViewModel
    private lateinit var fakeRepository: FakeCoverageRepository

    @Before
    fun setup() {
        fakeRepository = FakeCoverageRepository()
        viewModel = SearchCoverageViewModel(fakeRepository)
    }

    @Test
    fun `test monitorLogId updates correctly`() {
        // Initial value should be -1
        assertEquals(-1L, viewModel.monitorLogId.value)

        // Update monitorLogId and check if it's updated
        viewModel.setMonitorLogId(123L)
        assertEquals(123L, viewModel.monitorLogId.value)
    }

    @Test
    fun `test searchCoverageUiState updates when monitorLogId is set`() = runBlocking {
        // Prepare test data
        val coverage1 = Coverage(
            id = 1L,
            tracking = "Coverage 1",
            monitorLogId = 123L,
            change = "exampleChange1",
            code = "exampleCode1",
            coverage = "exampleCoverage1",
            cropType = "exampleCropType1",
            disturbance = "exampleDisturbance1"
        )

        val coverage2 = Coverage(
            id = 2L,
            tracking = "Coverage 2",
            monitorLogId = 123L,
            change = "exampleChange2",
            code = "exampleCode2",
            coverage = "exampleCoverage2",
            cropType = "exampleCropType2",
            disturbance = "exampleDisturbance2"
        )

        val coverage3 = Coverage(
            id = 3L,
            tracking = "Coverage 3",
            monitorLogId = 456L,
            change = "exampleChange3",
            code = "exampleCode3",
            coverage = "exampleCoverage3",
            cropType = "exampleCropType3",
            disturbance = "exampleDisturbance3"
        )


        // Insert data into the repository
        fakeRepository.insertConverage(coverage1)
        fakeRepository.insertConverage(coverage2)
        fakeRepository.insertConverage(coverage3)

        // Set monitorLogId to filter by 123L
        viewModel.setMonitorLogId(123L)

        // Verify the searchCoverageUiState emits the expected list
        val uiState = viewModel.searchCoverageUiState.first()
        assertEquals(listOf(coverage1, coverage2), uiState.coverage)
    }

    @Test
    fun `test deleteCoverage removes item from repository`() = runBlocking {
        // Prepare test data
        val coverage1 = Coverage(
            id = 1L,
            tracking = "Coverage 1",
            monitorLogId = 123L,
            change = "exampleChange1",
            code = "exampleCode1",
            coverage = "exampleCoverage1",
            cropType = "exampleCropType1",
            disturbance = "exampleDisturbance1"
        )

        val coverage2 = Coverage(
            id = 2L,
            tracking = "Coverage 2",
            monitorLogId = 123L,
            change = "exampleChange2",
            code = "exampleCode2",
            coverage = "exampleCoverage2",
            cropType = "exampleCropType2",
            disturbance = "exampleDisturbance2"
        )


        // Insert data into the repository
        fakeRepository.insertConverage(coverage1)
        fakeRepository.insertConverage(coverage2)

        // Verify initial state
        val initialState = viewModel.searchCoverageUiState.first()
        assertEquals(listOf(coverage1, coverage2), initialState.coverage)

        // Delete a coverage item
        viewModel.deleteCoverage(coverage1.id)

        // Verify the item was removed
        val updatedState = viewModel.searchCoverageUiState.first()
        assertEquals(listOf(coverage2), updatedState.coverage)
    }
}
