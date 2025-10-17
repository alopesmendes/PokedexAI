package com.ailtontech.pokedewai.features.pokemonDetail.data.repositories

import com.ailtontech.pokedewai.data.errors.Error
import com.ailtontech.pokedewai.features.pokemonDetail.data.datasources.remote.IPokemonDetailRemoteDatasource
import com.ailtontech.pokedewai.features.pokemonDetail.domain.failures.PokemonDetailFailure
import com.ailtontech.pokedewai.features.pokemonDetail.mocks.mockPokemonDetail
import com.ailtontech.pokedewai.features.pokemonDetail.mocks.mockPokemonDetailDto
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
class PokemonDetailRepositoryImplTest : BaseRepositoryTest() {

    private lateinit var remoteDatasource: IPokemonDetailRemoteDatasource
    private lateinit var repository: PokemonDetailRepositoryImpl

    @Before
    override fun setUp() {
        super.setUp()
        remoteDatasource = mockk()
        repository = PokemonDetailRepositoryImpl(remoteDatasource)
    }

    @Test
    fun `given successful datasource response when getPokemonDetail then return Success with PokemonDetail`() =
        runTest(testDispatcher) {
            // Given
            val pokemonDetailDto = mockPokemonDetailDto()
            val expectedPokemonDetail = mockPokemonDetail()
            coEvery { remoteDatasource.getPokemonDetail(any()) } returns pokemonDetailDto

            // When
            val result = repository.getPokemonDetail("bulbasaur")

            // Then
            assertTrue(result.isSuccess)
            assertEquals(expectedPokemonDetail, result.getOrNull())
        }

    @Test
    fun `given datasource error when getPokemonDetail then return Failure`() =
        runTest(testDispatcher) {
            // Given
            val exception = Error.NetworkError("No connection")
            coEvery { remoteDatasource.getPokemonDetail(any()) } throws exception

            // When
            val result = repository.getPokemonDetail("bulbasaur")

            // Then
            assertTrue(result.isFailure)
            assertTrue(result.exceptionOrNull() is PokemonDetailFailure.NetworkFailure)
        }
}
