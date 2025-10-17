package com.ailtontech.pokedewai.features.pokemonDetail.domain.failures

sealed class PokemonDetailFailure(
    override val message: String?,
    override val cause: Throwable?
) : Throwable(message, cause) {

    data class HttpFailure(
        override val message: String?,
        override val cause: Throwable?
    ) : PokemonDetailFailure(
        message = message,
        cause = cause,
    )

    data class NetworkFailure(
        override val message: String?,
        override val cause: Throwable?,
    ) : PokemonDetailFailure(
        message = message,
        cause = cause,
    )

    data class UnknownFailure(
        override val message: String?,
        override val cause: Throwable?,
    ) : PokemonDetailFailure(
        message = message,
        cause = cause,
    )

    data class SerializationFailure(
        override val message: String?,
        override val cause: Throwable?,
    ) : PokemonDetailFailure(
        message = message,
        cause = cause,
    )
}
