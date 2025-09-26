package com.ailtontech.pokedewai.data.datasources.remote

import com.ailtontech.pokedewai.data.errors.Error
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher // Added
import kotlinx.coroutines.test.UnconfinedTestDispatcher // Added
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException
import kotlinx.serialization.SerializationException
import io.mockk.every
import io.mockk.mockk

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
        var caughtError: Throwable? = null

        try {
            ApiServices.execute<Unit>(unconfinedTestDispatcher) {
                throw originalException
            }
        } catch (e: Throwable) {
            caughtError = e
        }

        assertTrue("Error.NetworkError was expected", caughtError is Error.NetworkError)
        assertEquals("Network error: Network connection lost", caughtError?.message)
        assertEquals(originalException, caughtError?.cause)
    }

    @Test
    fun `execute given apiCall throws ClientRequestException should throw HttpError`() = runTest(unconfinedTestDispatcher) {
        val mockKtorResponse = mockk<HttpResponse>()
        every { mockKtorResponse.status } returns HttpStatusCode.BadRequest

        val originalException = mockk<ClientRequestException>()
        every { originalException.response } returns mockKtorResponse
        every { originalException.cause } returns null
        every { originalException.message } returns "Mocked Ktor Client Error"

        var caughtError: Throwable? = null
        try {
            ApiServices.execute<Unit>(unconfinedTestDispatcher) {
                throw originalException
            }
        } catch (e: Throwable) {
            caughtError = e
        }

        assertTrue("Error.HttpError was expected", caughtError is Error.HttpError)
        val httpError = caughtError as Error.HttpError
        assertEquals(HttpStatusCode.BadRequest.value, httpError.statusCode)
        assertEquals("Client error: ${HttpStatusCode.BadRequest.description}", httpError.message)
        assertEquals(originalException, httpError.cause)
    }

    @Test
    fun `execute given apiCall throws ServerResponseException should throw HttpError`() = runTest(unconfinedTestDispatcher) {
        val mockKtorResponse = mockk<HttpResponse>()
        every { mockKtorResponse.status } returns HttpStatusCode.InternalServerError

        val originalException = mockk<ServerResponseException>()
        every { originalException.response } returns mockKtorResponse
        every { originalException.cause } returns null
        every { originalException.message } returns "Mocked Ktor Server Error"

        var caughtError: Throwable? = null
        try {
            ApiServices.execute<Unit>(unconfinedTestDispatcher) {
                throw originalException
            }
        } catch (e: Throwable) {
            caughtError = e
        }

        assertTrue("Error.HttpError was expected", caughtError is Error.HttpError)
        val httpError = caughtError as Error.HttpError
        assertEquals(HttpStatusCode.InternalServerError.value, httpError.statusCode)
        assertEquals("Server error: ${HttpStatusCode.InternalServerError.description}", httpError.message)
        assertEquals(originalException, httpError.cause)
    }

    @Test
    fun `execute given apiCall throws SerializationException should throw SerializationError`() = runTest(unconfinedTestDispatcher) {
        val originalException = SerializationException("Failed to parse JSON")
        var caughtError: Throwable? = null

        try {
            ApiServices.execute<Unit>(unconfinedTestDispatcher) {
                throw originalException
            }
        } catch (e: Throwable) {
            caughtError = e
        }

        assertTrue("Error.SerializationError was expected", caughtError is Error.SerializationError)
        assertEquals("Serialization error: Failed to parse JSON", caughtError?.message)
        assertEquals(originalException, caughtError?.cause)
    }

    @Test
    fun `execute given apiCall throws custom HttpError should re-throw it`() = runTest(unconfinedTestDispatcher) {
        val customError = Error.HttpError(404, "Custom Resource Not Found", null)
        var caughtError: Throwable? = null

        try {
            ApiServices.execute<Unit>(unconfinedTestDispatcher) {
                throw customError
            }
        } catch (e: Throwable) {
            caughtError = e
        }
        assertEquals(customError, caughtError)
    }

    @Test
    fun `execute given apiCall throws other Throwable should throw UnknownApiError`() = runTest(unconfinedTestDispatcher) {
        val originalException = IllegalArgumentException("Some other weird issue")
        var caughtError: Throwable? = null

        try {
            ApiServices.execute<Unit>(unconfinedTestDispatcher) {
                throw originalException
            }
        } catch (e: Throwable) {
            caughtError = e
        }

        assertTrue("Error.UnknownApiError was expected", caughtError is Error.UnknownApiError)
        assertEquals("An unknown API error occurred: Some other weird issue", caughtError?.message)
        assertEquals(originalException, caughtError?.cause)
    }
}
