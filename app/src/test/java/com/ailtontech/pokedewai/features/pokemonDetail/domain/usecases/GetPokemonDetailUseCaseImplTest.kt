package com.ailtontech.pokedewai.features.pokemonDetail.domain.usecases

import com.ailtontech.pokedewai.features.pokemonDetail.domain.commands.PokemonDetailParams
import com.ailtontech.pokedewai.features.pokemonDetail.domain.commands.PokemonDetailQuery
import com.ailtontech.pokedewai.features.pokemonDetail.domain.entities.PokemonDetail
import com.ailtontech.pokedewai.features.pokemonDetail.domain.failures.PokemonDetailFailure
import com.ailtontech.pokedewai.features.pokemonDetail.domain.repositories.IPokemonDetailRepository
import com.ailtontech.pokedewai.test_utils.BaseUseCaseTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class GetPokemonDetailUseCaseImplTest : BaseUseCaseTest() {

    private lateinit var repository: IPokemonDetailRepository
    private lateinit var useCase: GetPokemonDetailUseCaseImpl

    @Before
    override fun setUp() {
        super.setUp()
        repository = mockk()
        useCase = GetPokemonDetailUseCaseImpl(repository)
    }

    @Test
    fun `given repository success when invoke then return Success`() = runTest {
        // Given
        val pokemonDetail = mockk<PokemonDetail>()
        val params = PokemonDetailParams("bulbasaur")
        coEvery { repository.getPokemonDetail(any()) } returns Result.success(pokemonDetail)

        // When
        val result = useCase(params)

        // Then
        assertTrue(result is PokemonDetailQuery.Success)
        assertEquals(pokemonDetail, (result as PokemonDetailQuery.Success).pokemonDetail)
    }

    @Test
    fun `given repository failure when invoke then return Failure`() = runTest {
        // Given
        val failure = PokemonDetailFailure.NetworkFailure(
            message = "Network Failure",
            cause = IOException()
        )
        val params = PokemonDetailParams("bulbasaur")
        coEvery { repository.getPokemonDetail(any()) } returns Result.failure(failure)

        // When
        val result = useCase(params)

        // Then
        assertTrue(result is PokemonDetailQuery.Failure)
        assertEquals(failure, (result as PokemonDetailQuery.Failure).failure)
    }
}
