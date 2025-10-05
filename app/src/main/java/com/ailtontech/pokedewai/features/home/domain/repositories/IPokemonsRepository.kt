package com.ailtontech.pokedewai.features.home.domain.repositories

import com.ailtontech.pokedewai.features.home.domain.entities.PokemonList

interface IPokemonsRepository {
    suspend fun getPaginatedPokemons(
        count: Int,
        offset: Int,
        limit: Int
    ): Result<PokemonList>
}