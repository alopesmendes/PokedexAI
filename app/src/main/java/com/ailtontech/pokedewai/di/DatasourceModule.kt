package com.ailtontech.pokedewai.di

import com.ailtontech.pokedewai.features.home.data.datasources.remote.IPokemonFormRemoteDatasource
import com.ailtontech.pokedewai.features.home.data.datasources.remote.IPokemonListRemoteDatasource
import com.ailtontech.pokedewai.features.home.data.datasources.remote.impl.PokemonFormRemoteDatasourceImpl
import com.ailtontech.pokedewai.features.home.data.datasources.remote.impl.PokemonListRemoteDatasourceImpl
import com.ailtontech.pokedewai.features.pokemonDetail.data.datasources.remote.IPokemonDetailRemoteDatasource
import com.ailtontech.pokedewai.features.pokemonDetail.data.datasources.remote.PokemonDetailRemoteDatasourceImpl
import com.ailtontech.pokedewai.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
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
import org.koin.dsl.bind
import org.koin.dsl.module
import java.io.IOException

val datasourceModule: Module = module {
    // HttpClient definition (existing)
    single {
        HttpClient(Android) {
            defaultRequest {
                url(Constants.BASE_URL)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }

            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }

            install(Logging) {
                logger = Logger.ANDROID
                level = LogLevel.ALL
            }

            install(HttpTimeout) {
                requestTimeoutMillis = Constants.REQUEST_TIMEOUT_MILLIS
                connectTimeoutMillis = Constants.CONNECT_TIMEOUT_MILLIS
                socketTimeoutMillis = Constants.SOCKET_TIMEOUT_MILLIS
            }

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

    single {
        PokemonListRemoteDatasourceImpl(
            dispatcher = get(DispatcherQualifiers.Io),
            httpClient = get()
        )
    } bind IPokemonListRemoteDatasource::class

    single {
        PokemonFormRemoteDatasourceImpl(
            dispatcher = get(DispatcherQualifiers.Io),
            httpClient = get()
        )
    } bind IPokemonFormRemoteDatasource::class

    single {
        PokemonDetailRemoteDatasourceImpl(
            dispatcher = get(DispatcherQualifiers.Io),
            httpClient = get()
        )
    } bind IPokemonDetailRemoteDatasource::class
}
