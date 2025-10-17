package com.ailtontech.pokedewai.features.pokemonDetail.data.mappers

import com.ailtontech.pokedewai.data.errors.Error
import com.ailtontech.pokedewai.features.pokemonDetail.domain.failures.PokemonDetailFailure

fun Error.toPokemonDetailFailure(): PokemonDetailFailure {
    return when (this) {
        is Error.HttpError -> PokemonDetailFailure.HttpFailure(
            message = this.message,
            cause = this.cause
        )

        is Error.NetworkError -> PokemonDetailFailure.NetworkFailure(
            message = this.message,
            cause = this.cause
        )

        is Error.SerializationError -> PokemonDetailFailure.SerializationFailure(
            message = this.message,
            cause = this.cause
        )

        is Error.UnknownApiError -> PokemonDetailFailure.UnknownFailure(
            message = this.message,
            cause = this.cause
        )
    }
}