package com.ailtontech.pokedewai.features.home.domain.commands

import com.ailtontech.pokedewai.features.home.domain.entities.PokemonList
import com.ailtontech.pokedewai.features.home.failures.PokemonsFailure

sealed class PokemonListQuery {
    data class Success(
        val pokemonList: PokemonList
    ): PokemonListQuery()

    data class Error(
        val failure: PokemonsFailure
    ): PokemonListQuery()
}


