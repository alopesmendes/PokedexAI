package com.ailtontech.pokedewai.features.home.data.repositories

import com.ailtontech.pokedewai.data.errors.Error
import com.ailtontech.pokedewai.features.home.data.datasources.remote.IPokemonFormRemoteDatasource
import com.ailtontech.pokedewai.features.home.data.datasources.remote.IPokemonListRemoteDatasource
import com.ailtontech.pokedewai.features.home.domain.repositories.IPokemonsRepository
import com.ailtontech.pokedewai.features.home.failures.PokemonsFailure
import com.ailtontech.pokedewai.features.home.mocks.mockPokemonFormDto
import com.ailtontech.pokedewai.features.home.mocks.mockPokemonList
import com.ailtontech.pokedewai.features.home.mocks.mockPokemonListDto
import com.ailtontech.pokedewai.test_utils.BaseRepositoryTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class PokemonsRepositoryImplTest : BaseRepositoryTest() {

    private lateinit var pokemonListRemoteDatasource: IPokemonListRemoteDatasource
    private lateinit var pokemonFormRemoteDatasource: IPokemonFormRemoteDatasource
    private lateinit var repository: IPokemonsRepository

    @Before
    override fun setUp() {
        super.setUp()
        pokemonListRemoteDatasource = mockk()
        pokemonFormRemoteDatasource = mockk()
        repository = PokemonsRepositoryImpl(
            pokemonListRemoteDatasource = pokemonListRemoteDatasource,
            pokemonFormRemoteDatasource = pokemonFormRemoteDatasource,
        )
    }

    @Test
    fun `given successful responses, when getPaginatedPokemons is called, then return success`() = runTest {
        // Given
        val pokemonListDto = mockPokemonListDto(count = 100)
        val pokemonFormDto = mockPokemonFormDto()
        val pokemonList = mockPokemonList(count = 100)

        coEvery { pokemonListRemoteDatasource.getPokemonList(any(), any()) } returns pokemonListDto
        coEvery { pokemonFormRemoteDatasource.getPokemonForm(any()) } returns pokemonFormDto

        // When
        val result = repository.getPaginatedPokemons(count = 100, offset = 0, limit = 20)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(result.getOrNull(), pokemonList)
    }

    @Test
    fun `given list datasource error, when getPaginatedPokemons is called, then return failure`() = runTest {
        // Given
        coEvery { pokemonListRemoteDatasource.getPokemonList(any(), any()) } throws Error.NetworkError("No connection")

        // When
        val result = repository.getPaginatedPokemons(count = 1, offset = 0, limit = 1)

        // Then
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is PokemonsFailure.NetworkFailure)
    }

    @Test
    fun `given form datasource error, when getPaginatedPokemons is called, then return failure`() = runTest {
        // Given
        val pokemonListDto = mockPokemonListDto()
        coEvery { pokemonListRemoteDatasource.getPokemonList(any(), any()) } returns pokemonListDto
        coEvery { pokemonFormRemoteDatasource.getPokemonForm(any()) } throws Error.HttpError(404, "Not found")

        // When
        val result = repository.getPaginatedPokemons(count = 1, offset = 0, limit = 1)

        // Then
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is PokemonsFailure.HttpFailure)
    }

    @Test
    fun `given negative count, when getPaginatedPokemons is called, then return failure`() = runTest {
        // When
        val result = repository.getPaginatedPokemons(count = -1, offset = 0, limit = 1)

        // Then
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is PokemonsFailure.CountIsNegativeFailure)
    }

    @Test
    fun `given negative offset, when getPaginatedPokemons is called, then return failure`() = runTest {
        // When
        val result = repository.getPaginatedPokemons(count = 1, offset = -1, limit = 1)

        // Then
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is PokemonsFailure.OffsetIsNegativeFailure)
    }

    @Test
    fun `given zero limit, when getPaginatedPokemons is called, then return failure`() = runTest {
        // When
        val result = repository.getPaginatedPokemons(count = 1, offset = 0, limit = 0)

        // Then
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is PokemonsFailure.LimitIsZeroOrNegativeFailure)
    }

    @Test
    fun `given offset goes over total, when getPaginatedPokemons is called, then return failure`() = runTest {
        // When
        val result = repository.getPaginatedPokemons(count = 1, offset = 1, limit = 1)

        // Then
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is PokemonsFailure.OffsetGoesOverTotalFailure)
    }
}
