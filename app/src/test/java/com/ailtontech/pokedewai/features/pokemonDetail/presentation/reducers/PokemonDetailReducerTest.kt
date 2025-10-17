package com.ailtontech.pokedewai.features.pokemonDetail.presentation.reducers

import com.ailtontech.pokedewai.features.pokemonDetail.domain.commands.PokemonDetailParams
import com.ailtontech.pokedewai.features.pokemonDetail.domain.commands.PokemonDetailQuery
import com.ailtontech.pokedewai.features.pokemonDetail.domain.failures.PokemonDetailFailure
import com.ailtontech.pokedewai.features.pokemonDetail.domain.usecases.IGetPokemonDetailUseCase
import com.ailtontech.pokedewai.features.pokemonDetail.mocks.mockPokemonDetail
import com.ailtontech.pokedewai.features.pokemonDetail.presentation.reducers.events.PokemonDetailEvent
import com.ailtontech.pokedewai.presentation.reducers.Next
import com.ailtontech.pokedewai.test_utils.BaseReducerTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonDetailReducerTest : BaseReducerTest() {

    private lateinit var getPokemonDetailUseCase: IGetPokemonDetailUseCase
    private lateinit var reducer: PokemonDetailReducer
    private lateinit var initialState: PokemonDetailState

    @Before
    override fun setUp() {
        getPokemonDetailUseCase = mockk()
        reducer = PokemonDetailReducer(getPokemonDetailUseCase)
        initialState = PokemonDetailState()
    }

    @Test
    fun `invoke with Loading event returns state with isLoading true`() = runTest {
        // Given
        val event = PokemonDetailEvent.Loading

        // When
        val next = reducer.invoke(initialState, event)

        // Then
        val expectedState = initialState.copy(isLoading = true, failure = null)
        assertEquals(Next(state = expectedState, effect = null), next)
    }

    @Test
    fun `invoke with GetPokemonDetail event and success returns state with pokemon detail`() =
        runTest {
            // Given
            val pokemonName = "pikachu"
            val event = PokemonDetailEvent.GetPokemonDetail(pokemonName)
            val pokemonDetail = mockPokemonDetail()
            val params = PokemonDetailParams(name = pokemonName)
            val queryResult = PokemonDetailQuery.Success(pokemonDetail)

            coEvery { getPokemonDetailUseCase(params) } returns queryResult

            // When
            val next = reducer.invoke(initialState, event)

            // Then
            val expectedState = initialState.copy(isLoading = false, pokemon = pokemonDetail)
            assertEquals(Next(state = expectedState, effect = null), next)
        }

    @Test
    fun `invoke with GetPokemonDetail event and failure returns state with failure`() = runTest {
        // Given
        val pokemonName = "unknown"
        val event = PokemonDetailEvent.GetPokemonDetail(pokemonName)
        val failure = PokemonDetailFailure.UnknownFailure(
            message = "Unknown failure",
            cause = Exception("Unknown exception")
        )
        val params = PokemonDetailParams(name = pokemonName)
        val queryResult = PokemonDetailQuery.Failure(failure)

        coEvery { getPokemonDetailUseCase(params) } returns queryResult

        // When
        val next = reducer.invoke(initialState, event)

        // Then
        val expectedState = initialState.copy(isLoading = false, failure = failure)
        assertEquals(Next(state = expectedState, effect = null), next)
    }
}
