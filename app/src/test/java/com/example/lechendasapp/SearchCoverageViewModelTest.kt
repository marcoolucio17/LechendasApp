package com.example.lechendasapp

import com.example.lechendasapp.data.model.Coverage
import com.example.lechendasapp.FakeCoverageRepository
import com.example.lechendasapp.data.repository.CoverageRepository
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

    // Test unitario para garantizar que el logId del monitor si se actualiza correctamente
    @Test
    fun `test monitorLogId updates correctly`() {
        // valor inicial debe de ser 1
        assertEquals(-1L, viewModel.monitorLogId.value)

        // lo actualizamos y checamos que si se haya actualizado
        viewModel.setMonitorLogId(123L)
        assertEquals(123L, viewModel.monitorLogId.value)
    }


}
