package com.ailtontech.pokedewai.features.home.data.datasources.remote.impl

import com.ailtontech.pokedewai.data.datasources.remote.ApiServices
import com.ailtontech.pokedewai.features.home.data.datasources.remote.IPokemonFormRemoteDatasource
import com.ailtontech.pokedewai.features.home.data.models.PokemonFormDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Implementation of [IPokemonFormRemoteDatasource] that fetches data
 * from a remote HTTP server using Ktor.
 *
 * @param httpClient The Ktor HttpClient instance.
 * @param dispatcher The coroutine dispatcher for API calls.
 */
class PokemonFormRemoteDatasourceImpl(
    private val dispatcher: CoroutineDispatcher,
    private val httpClient: HttpClient
) : IPokemonFormRemoteDatasource {

    /**
     * Fetches details for a specific Pokemon Form by its ID.
     *
     * The actual API call is wrapped with [ApiServices.execute] to handle
     * potential errors and map them to custom [com.ailtontech.pokedewai.data.errors.Error] types.
     *
     * @param name The name of the Pokemon Form.
     * @return A [PokemonFormDto] containing the details of the Pokemon Form.
     * @throws com.ailtontech.pokedewai.data.errors.Error for API, network, or parsing issues,
     *         as processed by [ApiServices.execute].
     */
    override suspend fun getPokemonForm(name: String): PokemonFormDto {
        return ApiServices.execute(dispatcher) {
            httpClient.get("pokemon-form/$name").body()
        }
    }
}
