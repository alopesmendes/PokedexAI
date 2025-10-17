package com.ailtontech.pokedewai.features.pokemonDetail.data.datasources.remote

import com.ailtontech.pokedewai.data.errors.Error
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
class PokemonDetailRemoteDatasourceImplTest : BaseRemoteDatasourceTest() {

    private lateinit var datasource: IPokemonDetailRemoteDatasource

    @Before
    override fun setUp() {
        super.setUp()
        datasource = PokemonDetailRemoteDatasourceImpl(
            dispatcher = testDispatcher,
            httpClient = httpClient
        )
    }

    @Test
    fun `given a valid response, when getPokemonDetail is called, then it should return a PokemonDetailDto`() =
        runTest {
            // Given
            val json = JsonReader.readJsonFile("pokemon_detail_success.json")
            prepareSuccessResponse(json)

            // When
            val result = datasource.getPokemonDetail("clefairy")

            // Then
            assertEquals(35, result.id)
            assertEquals("clefairy", result.name)
            assertEquals(6, result.height)
            assertEquals(75, result.weight)
            assertEquals(113, result.baseExperience)
            assertEquals(64, result.order)
            assertEquals(true, result.isDefault)
        }

    @Test
    fun `given a 404 error, when getPokemonDetail is called, then it should throw HttpError`() =
        runTest {
            // Given
            prepareHttpErrorResponse(HttpStatusCode.NotFound)

            // When & Then
            val exception = assertFailsWith<Error.HttpError> {
                datasource.getPokemonDetail("invalid_pokemon")
            }
            assertEquals(404, exception.statusCode)
        }

    @Test
    fun `given a network error, when getPokemonDetail is called, then it should throw NetworkError`() =
        runTest {
            // Given
            prepareNetworkError()

            // When & Then
            assertFailsWith<Error.NetworkError> {
                datasource.getPokemonDetail("bulbasaur")
            }
        }

    @Test
    fun `given a malformed json, when getPokemonDetail is called, then it should throw SerializationError`() =
        runTest {
            // Given
            prepareSuccessResponse("""{"invalid":"json"}""")

            // When & Then
            val exception = assertFailsWith<Error.SerializationError> {
                datasource.getPokemonDetail("bulbasaur")
            }
            assertNotNull(exception.cause)
        }
}
