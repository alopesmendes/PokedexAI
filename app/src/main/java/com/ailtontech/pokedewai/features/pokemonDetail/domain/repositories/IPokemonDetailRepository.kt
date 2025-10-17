package com.ailtontech.pokedewai.features.pokemonDetail.domain.repositories

import com.ailtontech.pokedewai.features.pokemonDetail.domain.entities.PokemonDetail

interface IPokemonDetailRepository {
    suspend fun getPokemonDetail(name: String): Result<PokemonDetail>
}
