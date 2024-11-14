import com.example.lechendasapp.data.model.Coverage
import com.example.lechendasapp.data.repository.CoverageRepository
import com.example.lechendasapp.fakes.FakeCoverageRepository
import com.example.lechendasapp.viewmodels.SearchCoverageUiState
import com.example.lechendasapp.viewmodels.SearchCoverageViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchCoverageViewModelTest {

    private lateinit var coverageRepository: CoverageRepository
    private lateinit var viewModel: SearchCoverageViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        coverageRepository = FakeCoverageRepository()
        viewModel = SearchCoverageViewModel(coverageRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
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


    @Test
    fun `deleteCoverage should remove the coverage from the repository`() = runTest {
        // Given
        val coverage = Coverage(
            id = 1, monitorLogId = 123, code = "Coverage1",
            tracking = "tacking",
            change = "change",
            coverage = "coverage",
            cropType = "cropType",
            disturbance = "disturbance",
            observations = "observations"
        )
        (coverageRepository as FakeCoverageRepository).insertConverage(coverage)

        // When
        viewModel.deleteCoverage(1L)
        advanceUntilIdle()

        // Then
        val coverages = (coverageRepository as FakeCoverageRepository).getConverage()
        assertTrue(coverages.none { it.id == 1L })
    }

    @Test
    fun `initial searchCoverageUiState should be empty`() = runTest {
        // When
        val uiState = viewModel.searchCoverageUiState.first()

        // Then
        assertTrue(uiState.coverage.isEmpty())
    }
}
