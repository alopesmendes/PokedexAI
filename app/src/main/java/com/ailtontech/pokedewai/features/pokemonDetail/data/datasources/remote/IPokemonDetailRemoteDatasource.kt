package com.ailtontech.pokedewai.features.pokemonDetail.data.datasources.remote

import com.ailtontech.pokedewai.features.pokemonDetail.data.models.PokemonDetailDto

interface IPokemonDetailRemoteDatasource {
    suspend fun getPokemonDetail(name: String): PokemonDetailDto
}
