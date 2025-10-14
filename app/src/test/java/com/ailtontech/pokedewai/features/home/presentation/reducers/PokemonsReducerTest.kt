package com.ailtontech.pokedewai.features.home.presentation.reducers

import com.ailtontech.pokedewai.features.home.domain.commands.PokemonListQuery
import com.ailtontech.pokedewai.features.home.domain.useCases.IGetPokemonsUseCase
import com.ailtontech.pokedewai.features.home.failures.PokemonsFailure
import com.ailtontech.pokedewai.features.home.mocks.mockPokemonList
import com.ailtontech.pokedewai.features.home.presentation.mappers.toCardModel
import com.ailtontech.pokedewai.features.home.presentation.reducers.effects.PokemonsEffect
import com.ailtontech.pokedewai.features.home.presentation.reducers.events.PokemonsEvent
import com.ailtontech.pokedewai.test_utils.BaseReducerTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class PokemonsReducerTest : BaseReducerTest() {

    private lateinit var getPokemonsUseCase: IGetPokemonsUseCase
    private lateinit var reducer: PokemonsReducer

    @Before
    override fun setUp() {
        super.setUp()
        getPokemonsUseCase = mockk()
        reducer = PokemonsReducer(getPokemonsUseCase)
    }

    @Test
    fun `given PokemonsListLoading event, when reducer is invoked, then state should be loading`() =
        runTest {
            // Given
            val initialState = PokemonsState()
            val event = PokemonsEvent.PokemonsListLoading

            // When
            val next = reducer(initialState, event)

            // Then
            assertTrue(next.state!!.isLoading)
        }

    @Test
    fun `given GetPokemonsList event and use case success, when reducer is invoked, then state should be updated with new data`() =
        runTest {
            // Given
            val initialState = PokemonsState()
            val event = PokemonsEvent.GetPokemonsList
            val pokemonList = mockPokemonList(
                offset = 20,
                limit = 20,
                count = 50
            )
            val successResult = PokemonListQuery.Success(pokemonList)
            coEvery { getPokemonsUseCase(any()) } returns successResult

            // When
            val next = reducer(initialState, event)

            // Then
            assertFalse(next.state.isLoading)
            assertEquals(pokemonList.pokemonForms.map { it.toCardModel() }, next.state.pokemons)
            assertEquals(pokemonList.count, next.state.count)
            assertEquals(pokemonList.offset, next.state.offset)
            assertEquals(pokemonList.limit, next.state.limit)
            assertNull(next.state.failure)
        }

    @Test
    fun `given GetPokemonsList event and use case failure, when reducer is invoked, then state should be updated with failure`() =
        runTest {
            // Given
            val initialState = PokemonsState()
            val event = PokemonsEvent.GetPokemonsList
            val failure = PokemonsFailure.NetworkFailure("No connection")
            val errorResult = PokemonListQuery.Error(failure)
            coEvery { getPokemonsUseCase(any()) } returns errorResult

            // When
            val next = reducer(initialState, event)

            // Then
            assertFalse(next.state.isLoading)
            assertEquals(failure, next.state.failure)
        }

    @Test
    fun `given GetPokemonDetail event, when reducer is invoked, then it should emit NavigateToPokemonDetail effect`() =
        runTest {
            // Given
            val initialState = PokemonsState()
            val event = PokemonsEvent.GetPokemonDetail(id = 1)

            // When
            val next = reducer(initialState, event)

            // Then
            assertTrue(next.effect is PokemonsEffect.NavigateToPokemonDetail)
        }
}
