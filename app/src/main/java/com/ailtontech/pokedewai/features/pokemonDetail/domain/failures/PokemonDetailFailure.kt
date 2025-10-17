package com.ailtontech.pokedewai.features.pokemonDetail.domain.failures

import com.ailtontech.pokedewai.data.errors.Error

sealed class PokemonDetailFailure(
    override val message: String?,
    override val cause: Throwable?
) : Throwable(message, cause) {

    data class HttpFailure(
        val error: Error.HttpError
    ) : PokemonDetailFailure(error.message, error.cause)

    data class NetworkFailure(
        val error: Error.NetworkError
    ) : PokemonDetailFailure(error.message, error.cause)

    data class UnknownFailure(
        val error: Error.UnknownApiError
    ) : PokemonDetailFailure(error.message, error.cause)

    data class SerializationFailure(
        val error: Error.SerializationError
    ) : PokemonDetailFailure(error.message, error.cause)
}
