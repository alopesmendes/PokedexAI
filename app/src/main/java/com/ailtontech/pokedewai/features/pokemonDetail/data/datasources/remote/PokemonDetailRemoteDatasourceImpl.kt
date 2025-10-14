package com.ailtontech.pokedewai.features.pokemonDetail.data.datasources.remote

import com.ailtontech.pokedewai.data.datasources.remote.ApiServices
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.PokemonDetailDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineDispatcher

class PokemonDetailRemoteDatasourceImpl(
    private val dispatcher: CoroutineDispatcher,
    private val httpClient: HttpClient
) : IPokemonDetailRemoteDatasource {
    override suspend fun getPokemonDetail(name: String): PokemonDetailDto {
        return ApiServices.execute(dispatcher) {
            httpClient.get("pokemon/$name").body()
        }
    }
}
