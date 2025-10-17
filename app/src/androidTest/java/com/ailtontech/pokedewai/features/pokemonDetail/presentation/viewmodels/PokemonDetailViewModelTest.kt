package com.ailtontech.pokedewai.features.pokemonDetail.presentation.viewmodels

import com.ailtontech.pokedewai.features.pokemonDetail.presentation.reducers.PokemonDetailReducer
import com.ailtontech.pokedewai.features.pokemonDetail.presentation.reducers.PokemonDetailState
import com.ailtontech.pokedewai.features.pokemonDetail.presentation.reducers.events.PokemonDetailEvent
import com.ailtontech.pokedewai.features.pokemonDetail.presentation.viewModels.PokemonDetailViewModel
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
class PokemonDetailViewModelTest : BaseViewModelTest() {

    private lateinit var reducer: PokemonDetailReducer
    private lateinit var viewModel: PokemonDetailViewModel

    @Before
    fun setUp() {
        reducer = mockk(relaxed = true)
    }

    @Test
    fun givenPokemonNameWhenGetPokemonIsCalledThenSendsLoadingAndGetPokemonDetailEvents() =
        runTest {
            // Given
            val pokemonName = "pikachu"
            coEvery { reducer.invoke(any(), any()) } returns Next(
                state = PokemonDetailState(),
                effect = null
            )
            viewModel = PokemonDetailViewModel(reducer)

            // When
            viewModel.getPokemon(pokemonName)
            advanceUntilIdle()

            // Then
            coVerify(exactly = 1) {
                reducer.invoke(state = any(), event = PokemonDetailEvent.Loading)
            }
            coVerify(exactly = 1) {
                reducer.invoke(
                    state = any(),
                    event = PokemonDetailEvent.GetPokemonDetail(pokemonName)
                )
            }
        }
}
