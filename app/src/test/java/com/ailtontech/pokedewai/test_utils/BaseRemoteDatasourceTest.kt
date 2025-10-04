package com.ailtontech.pokedewai.test_utils

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.serialization.json.Json
import org.junit.Before

@ExperimentalCoroutinesApi
abstract class BaseRemoteDatasourceTest {

    private lateinit var mockEngine: MockEngine
    protected lateinit var httpClient: HttpClient
    protected lateinit var testDispatcher: TestDispatcher

    private val testJson = Json {
        isLenient = true
        ignoreUnknownKeys = true
        prettyPrint = true
        encodeDefaults = true // Important for DTOs
    }

    private var mockEngineHandler: suspend MockRequestHandleScope.(HttpRequestData) -> HttpResponseData = {
        error(
            "Unhandled request: ${it.url}. " +
                "Please use prepareSuccessResponse, prepareHttpErrorResponse, or prepareNetworkError in your test setup."
        )
    }

    @Before
    open fun setUp() {
        testDispatcher = UnconfinedTestDispatcher()
        mockEngine = MockEngine { request ->
            mockEngineHandler(this, request)
        }
        httpClient = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(testJson)
            }
        }
    }

    protected fun prepareSuccessResponse(jsonBody: String) {
        mockEngineHandler = {
            respond(
                content = jsonBody,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            )
        }
    }

    protected fun prepareHttpErrorResponse(
        statusCode: HttpStatusCode,
        errorBody: String = "Mocked HTTP Error"
    ) {
        mockEngineHandler = {
            respond(
                content = errorBody,
                status = statusCode,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Text.Plain.toString())
            )
        }
    }

    protected fun prepareNetworkError(exception: Throwable = java.io.IOException("Mocked network error")) {
        mockEngineHandler = {
            throw exception
        }
    }
}
