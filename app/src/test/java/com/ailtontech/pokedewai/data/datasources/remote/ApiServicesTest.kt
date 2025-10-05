package com.ailtontech.pokedewai.data.datasources.remote

import com.ailtontech.pokedewai.data.errors.Error
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.IOException
import kotlin.test.assertFailsWith
import kotlinx.serialization.SerializationException

@ExperimentalCoroutinesApi
class ApiServicesTest {

    private val unconfinedTestDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @Test
    fun `execute given successful apiCall should return result`() = runTest(unconfinedTestDispatcher) {
        val expectedData = "Test Success Data"
        val result = ApiServices.execute(unconfinedTestDispatcher) {
            expectedData
        }
        assertEquals(expectedData, result)
    }

    @Test
    fun `execute given apiCall throws IOException should throw NetworkError`() = runTest(unconfinedTestDispatcher) {
        val originalException = IOException("Network connection lost")

        val exception = assertFailsWith<Error.NetworkError> {
            ApiServices.execute<Unit>(unconfinedTestDispatcher) {
                throw originalException
            }
        }
        assertEquals("Network error: Network connection lost", exception.message)
        assertEquals(originalException, exception.cause)
    }

    @Test
    fun `execute given apiCall throws ClientRequestException should throw HttpError`() = runTest(unconfinedTestDispatcher) {
        val mockKtorResponse = mockk<HttpResponse>()
        every { mockKtorResponse.status } returns HttpStatusCode.BadRequest

        val originalException = mockk<ClientRequestException>()
        every { originalException.response } returns mockKtorResponse
        every { originalException.cause } returns null
        every { originalException.message } returns "Mocked Ktor Client Error"

        val exception = assertFailsWith<Error.HttpError> {
            ApiServices.execute<Unit>(unconfinedTestDispatcher) {
                throw originalException
            }
        }

        assertEquals(HttpStatusCode.BadRequest.value, exception.statusCode)
        assertEquals("Client request failed: Bad Request", exception.message)
        assertEquals(originalException, exception.cause)
    }

    @Test
    fun `execute given apiCall throws ServerResponseException should throw HttpError`() = runTest(unconfinedTestDispatcher) {
        val mockKtorResponse = mockk<HttpResponse>()
        every { mockKtorResponse.status } returns HttpStatusCode.InternalServerError

        val originalException = mockk<ServerResponseException>()
        every { originalException.response } returns mockKtorResponse
        every { originalException.cause } returns null
        every { originalException.message } returns "Mocked Ktor Server Error"

        val exception = assertFailsWith<Error.HttpError> {
            ApiServices.execute<Unit>(unconfinedTestDispatcher) {
                throw originalException
            }
        }

        assertEquals(HttpStatusCode.InternalServerError.value, exception.statusCode)
        assertEquals("Server response error: Internal Server Error", exception.message)
        assertEquals(originalException, exception.cause)
    }

    @Test
    fun `execute given apiCall throws SerializationException should throw SerializationError`() = runTest(unconfinedTestDispatcher) {
        val originalException = SerializationException("Failed to parse JSON")

        val exception = assertFailsWith<Error.SerializationError> {
            ApiServices.execute<Unit>(unconfinedTestDispatcher) {
                throw originalException
            }
        }
        assertEquals("Serialization error: Failed to parse JSON", exception.message)
        assertEquals(originalException, exception.cause)
    }

    @Test
    fun `execute given apiCall throws custom HttpError should re-throw it`() = runTest(unconfinedTestDispatcher) {
        val customError = Error.HttpError(404, "Custom Resource Not Found", null)

        val exception = assertFailsWith<Error.HttpError> {
            ApiServices.execute<Unit>(unconfinedTestDispatcher) {
                throw customError
            }
        }
        assertEquals(customError, exception)
    }

    @Test
    fun `execute given apiCall throws other Throwable should throw UnknownApiError`() = runTest(unconfinedTestDispatcher) {
        val originalException = IllegalArgumentException("Some other weird issue")
        val exception = assertFailsWith<Error.UnknownApiError> {
            ApiServices.execute<Unit>(unconfinedTestDispatcher) {
                throw originalException
            }
        }
        assertEquals("An unknown API error occurred: Some other weird issue", exception.message)
        assertEquals(originalException, exception.cause)
    }
}
