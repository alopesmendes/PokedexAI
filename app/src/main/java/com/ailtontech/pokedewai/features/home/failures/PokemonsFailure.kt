package com.ailtontech.pokedewai.features.home.failures

sealed class PokemonsFailure(
    override val message: String?,
    override val cause: Throwable?
): Throwable() {
    data class NetworkFailure(
        override val message: String?,
        override val cause: Throwable? = null
    ) : PokemonsFailure(
        message = message,
        cause = cause,
    )

    data class HttpFailure(
        val statusCode: Int,
        override val message: String?,
        override val cause: Throwable? = null,
    ) : PokemonsFailure(
        message = message,
        cause = cause,
    )

    data class UnknownFailure(
        override val message: String?,
        override val cause: Throwable? = null
    ) : PokemonsFailure(
        message = message,
        cause = cause,
    )

    class OffsetGoesOverTotalFailure() : PokemonsFailure(
        message = "Offset goes over total",
        cause = IllegalStateException(),
    )

    class LimitIsZeroOrNegativeFailure() : PokemonsFailure(
        message = "Limit must be greater than zero",
        cause = IllegalStateException(),
    )
    class OffsetIsNegativeFailure() : PokemonsFailure(
        message = "Offset must be greater than zero",
        cause = IllegalStateException(),
    )

    class CountIsNegativeFailure() : PokemonsFailure(
        message = "Count must be greater than zero",
        cause = IllegalStateException(),
    )
}