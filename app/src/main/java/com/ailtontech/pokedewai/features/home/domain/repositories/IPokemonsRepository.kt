package com.ailtontech.pokedewai.features.home.domain.repositories

import com.ailtontech.pokedewai.features.home.domain.entities.PokemonList

interface IPokemonsRepository {
    suspend fun getPaginatedPokemons(
        count: Int? = null,
        offset: Int,
        limit: Int
    ): Result<PokemonList>
}