package com.ailtontech.pokedewai.features.pokemonDetail.data.mappers

import com.ailtontech.pokedewai.data.errors.Error
import com.ailtontech.pokedewai.features.pokemonDetail.domain.failures.PokemonDetailFailure

fun Error.toPokemonDetailFailure(): PokemonDetailFailure {
    return when (this) {
        is Error.HttpError -> PokemonDetailFailure.HttpFailure(this)
        is Error.NetworkError -> PokemonDetailFailure.NetworkFailure(this)
        is Error.SerializationError -> PokemonDetailFailure.SerializationFailure(this)
        is Error.UnknownApiError -> PokemonDetailFailure.UnknownFailure(this)
    }
}