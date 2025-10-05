package com.ailtontech.pokedewai.features.home.data.mappers

import com.ailtontech.pokedewai.data.errors.Error
import com.ailtontech.pokedewai.features.home.failures.PokemonsFailure

fun Error.toPokemonsFailure(): PokemonsFailure {
    return when (this) {
        is Error.NetworkError, is Error.SerializationError -> PokemonsFailure.NetworkFailure(message = message)
        is Error.HttpError -> PokemonsFailure.HttpFailure(
            statusCode = statusCode,
            message = message
        )
        is Error.UnknownApiError -> PokemonsFailure.UnknownFailure(message = message)
    }
}