package com.ailtontech.pokedewai.features.home.data.datasources.remote.impl

import com.ailtontech.pokedewai.data.errors.Error
import com.ailtontech.pokedewai.features.home.data.datasources.remote.IPokemonFormRemoteDatasource
import com.ailtontech.pokedewai.test_utils.BaseRemoteDatasourceTest
import com.ailtontech.pokedewai.test_utils.JsonReader
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFailsWith

@ExperimentalCoroutinesApi
class PokemonFormRemoteDatasourceImplTest : BaseRemoteDatasourceTest() {

    private lateinit var datasource: IPokemonFormRemoteDatasource

    @Before
    override fun setUp() {
        super.setUp()
        datasource = PokemonFormRemoteDatasourceImpl(
            dispatcher = testDispatcher,
            httpClient = httpClient
        )
    }

    @Test
    fun `given a valid response, when getPokemonForm is called, then it should return a PokemonFormDto`() = runTest {
        // Given
        val json = JsonReader.readJsonFile("pokemon_form_success.json")
        prepareSuccessResponse(json)

        // When
        val result = datasource.getPokemonForm(id = 1)

        // Then
        assertEquals(1, result.id)
        assertEquals("bulbasaur", result.name)
    }

    @Test
    fun `given a 404 error, when getPokemonForm is called, then it should throw HttpError`() = runTest {
        // Given
        prepareHttpErrorResponse(HttpStatusCode.NotFound)

        // When & Then
        val exception = assertFailsWith<Error.HttpError> {
            datasource.getPokemonForm(id = -1)
        }
        assertEquals(404, exception.statusCode)
    }

    @Test
    fun `given a network error, when getPokemonForm is called, then it should throw NetworkError`() = runTest {
        // Given
        prepareNetworkError()

        // When & Then
        assertFailsWith<Error.NetworkError> {
            datasource.getPokemonForm(id = 1)
        }
    }

    @Test
    fun `given a malformed json, when getPokemonForm is called, then it should throw SerializationError`() = runTest {
        // Given
        prepareSuccessResponse("""{"invalid":"json"}""")

        // When & Then
        val exception = assertFailsWith<Error.SerializationError> {
            datasource.getPokemonForm(id = 1)
        }
        assertNotNull(exception.cause)
    }
}
