import com.ailtontech.pokedewai.features.home.presentation.reducers.PokemonsReducer
import com.ailtontech.pokedewai.features.home.presentation.reducers.PokemonsState
import com.ailtontech.pokedewai.features.home.presentation.reducers.events.PokemonsEvent
import com.ailtontech.pokedewai.features.home.presentation.viewModels.PokemonsViewModel
import com.ailtontech.pokedewai.presentation.reducers.Next
import com.ailtontech.pokedewai.test_utils.BaseViewModelTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class PokemonsViewModelTest : BaseViewModelTest() {

    private lateinit var reducer: PokemonsReducer
    private lateinit var viewModel: PokemonsViewModel

    @Before
    fun setUp() {
        reducer = mockk(relaxed = true)
    }

    @Test
    fun givenViewModelWhenInitializedThenPokemonListLoadingAndGetPokemonsListIsCalled() = runTest {
        // Given
        coEvery { reducer.invoke(any(), any()) } returns Next(
            state = PokemonsState(),
            effect = null
        )

        // When
        viewModel = PokemonsViewModel(reducer)
        advanceUntilIdle()

        // Then
        coVerify(exactly = 1) {
            reducer.invoke(state = any(), event = PokemonsEvent.PokemonsListLoading)
        }
        coVerify(exactly = 1) {
            reducer.invoke(
                state = any(),
                event = PokemonsEvent.GetPokemonsList(
                    offset = any(),
                    limit = any(),
                ),
            )
        }
    }
}