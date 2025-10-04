package com.ailtontech.pokedewai.features.home.data.datasources.remote.impl

import com.ailtontech.pokedewai.data.datasources.remote.ApiServices
import com.ailtontech.pokedewai.features.home.data.datasources.remote.IPokemonListRemoteDatasource
import com.ailtontech.pokedewai.features.home.data.models.PokemonListDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.CoroutineDispatcher

class PokemonListRemoteDatasourceImpl(
    private val dispatcher: CoroutineDispatcher,
    private val httpClient: HttpClient
) : IPokemonListRemoteDatasource {

    override suspend fun getPokemonList(offset: Int, limit: Int): PokemonListDto {
        return ApiServices.execute(dispatcher) {
            httpClient.get("pokemon") {
                parameter("offset", offset)
                parameter("limit", limit)
            }.body()
        }
    }
}
