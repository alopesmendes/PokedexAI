package com.ailtontech.pokedewai.features.home.domain.useCases.impl

import com.ailtontech.pokedewai.features.home.domain.commands.PokemonListQuery
import com.ailtontech.pokedewai.features.home.domain.commands.PokemonPaginationParams
import com.ailtontech.pokedewai.features.home.domain.repositories.IPokemonsRepository
import com.ailtontech.pokedewai.features.home.failures.PokemonsFailure
import com.ailtontech.pokedewai.features.home.mocks.mockPokemonList
import com.ailtontech.pokedewai.test_utils.BaseUseCaseTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.test.fail

@ExperimentalCoroutinesApi
class GetPokemonsUseCaseImplTest : BaseUseCaseTest() {

    private lateinit var repository: IPokemonsRepository
    private lateinit var useCase: GetPokemonsUseCaseImpl

    @Before
    override fun setUp() {
        super.setUp()
        repository = mockk()
        useCase = GetPokemonsUseCaseImpl(repository)
    }

    @Test
    fun `given repository success, when invoke is called, then return success`() = runTest {
        // Given
        val pokemonList = mockPokemonList()
        val command = PokemonPaginationParams(count = 100, offset = 0, limit = 20)
        coEvery { repository.getPaginatedPokemons(any(), any(), any()) } returns Result.success(pokemonList)

        // When
        val result = useCase(command)

        // Then
        when(result) {
            is PokemonListQuery.Success -> {
                assertEquals(pokemonList, result.pokemonList)
            }
            is PokemonListQuery.Error -> {
                fail("Should be a success, but got an failure instead: ${result.failure}")
            }
        }
    }

    @Test
    fun `given repository failure, when invoke is called, then return failure`() = runTest {
        // Given
        val failure = PokemonsFailure.NetworkFailure("No connection")
        val command = PokemonPaginationParams(count = 100, offset = 0, limit = 20)
        coEvery { repository.getPaginatedPokemons(any(), any(), any()) } returns Result.failure(failure)

        // When
        val result = useCase(command)

        // Then
        when(result) {
            is PokemonListQuery.Success -> {
                fail("Should be a failure, but got a success instead: ${result.pokemonList}")
            }
            is PokemonListQuery.Error -> {
                assertEquals(failure, result.failure)
            }
        }
    }
}
