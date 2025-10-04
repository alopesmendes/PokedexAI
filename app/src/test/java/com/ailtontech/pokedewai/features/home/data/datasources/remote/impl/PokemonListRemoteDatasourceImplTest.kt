package com.ailtontech.pokedewai.features.home.data.datasources.remote.impl

import com.ailtontech.pokedewai.data.errors.Error
import com.ailtontech.pokedewai.features.home.data.datasources.remote.IPokemonListRemoteDatasource
import com.ailtontech.pokedewai.test_utils.BaseRemoteDatasourceTest
import com.ailtontech.pokedewai.test_utils.JsonReader
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFailsWith

@ExperimentalCoroutinesApi
class PokemonListRemoteDatasourceImplTest : BaseRemoteDatasourceTest() {

    private lateinit var datasource: IPokemonListRemoteDatasource

    @Before
    override fun setUp() {
        super.setUp()
        datasource = PokemonListRemoteDatasourceImpl(
            dispatcher = testDispatcher,
            httpClient = httpClient
        )
    }

    @Test
    fun `given a valid response, when getPokemonList is called, then it should return a PokemonListDto`() = runTest {
        // Given
        val json = JsonReader.readJsonFile("pokemon_list_success.json")
        prepareSuccessResponse(json)

        // When
        val result = datasource.getPokemonList(offset = 0, limit = 20)

        // Then
        assertEquals(1302, result.count)
        assertEquals("https://pokeapi.co/api/v2/pokemon?offset=20&limit=20", result.next)
        assertEquals(null, result.previous)
        assertEquals(20, result.results.size)
        assertEquals("bulbasaur", result.results.first().name)
    }

    @Test
    fun `given a 404 error, when getPokemonList is called, then it should throw HttpError`() = runTest {
        // Given
        prepareHttpErrorResponse(HttpStatusCode.NotFound)

        // When & Then
        val exception = assertFailsWith<Error.HttpError> {
            datasource.getPokemonList(offset = 9999, limit = 20)
        }
        assertEquals(404, exception.statusCode)
    }

    @Test
    fun `given a network error, when getPokemonList is called, then it should throw NetworkError`() = runTest {
        // Given
        prepareNetworkError()

        // When & Then
        assertFailsWith<Error.NetworkError> {
            datasource.getPokemonList(offset = 0, limit = 20)
        }
    }

    @Test
    fun `given a malformed json, when getPokemonList is called, then it should throw SerializationError`() = runTest {
        // Given
        prepareSuccessResponse("""{"invalid":"json"}""")

        // When & Then
        val exception = assertFailsWith<Error.SerializationError> {
            datasource.getPokemonList(offset = 0, limit = 20)
        }
        assertNotNull(exception.cause)
    }

    @Test
    fun `given zero limit, when getPokemonList is called, then it should return an empty list`() = runTest {
        // Given
        val json = JsonReader.readJsonFile("pokemon_list_zero_limit.json")
        prepareSuccessResponse(json)

        // When
        val result = datasource.getPokemonList(offset = 0, limit = 0)

        // Then
        assertTrue(result.results.isEmpty())
    }
}
