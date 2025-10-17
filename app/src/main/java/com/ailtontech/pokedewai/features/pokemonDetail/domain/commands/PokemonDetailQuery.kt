package com.ailtontech.pokedewai.features.pokemonDetail.domain.commands

import com.ailtontech.pokedewai.features.pokemonDetail.domain.entities.PokemonDetail
import com.ailtontech.pokedewai.features.pokemonDetail.domain.failures.PokemonDetailFailure

sealed class PokemonDetailQuery {
    data class Success(
        val pokemonDetail: PokemonDetail
    ) : PokemonDetailQuery()

    data class Failure(
        val failure: PokemonDetailFailure
    ) : PokemonDetailQuery()
}
