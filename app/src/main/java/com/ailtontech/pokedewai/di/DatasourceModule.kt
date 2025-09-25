package com.ailtontech.pokedewai.di

import com.ailtontech.pokedewai.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest // Import defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.IOException

val datasourceModule: Module = module {
    single {
        HttpClient(Android) {
            defaultRequest {
                url(Constants.BASE_URL)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }

            // Content Negotiation for JSON
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true // Useful for evolving APIs
                })
            }

            // Logging for HTTP requests and responses
            install(Logging) {
                logger = Logger.ANDROID
                level = LogLevel.ALL
            }

            // HTTP Timeout Configuration
            install(HttpTimeout) {
                requestTimeoutMillis = Constants.REQUEST_TIMEOUT_MILLIS
                connectTimeoutMillis = Constants.CONNECT_TIMEOUT_MILLIS
                socketTimeoutMillis = Constants.SOCKET_TIMEOUT_MILLIS
            }

            // HTTP Request Retry Configuration
            install(HttpRequestRetry) {
                maxRetries = Constants.MAX_RETRIES
                delayMillis { Constants.RETRY_REQUEST_DELAY }
                retryOnExceptionIf { _, cause ->
                    cause is ClientRequestException ||
                            cause is ServerResponseException ||
                            cause is IOException
                }
                retryIf { _, response ->
                    !response.status.isSuccess()
                }
            }
        }
    }
}
