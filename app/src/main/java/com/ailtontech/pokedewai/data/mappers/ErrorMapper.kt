package com.ailtontech.pokedewai.data.mappers

import com.ailtontech.pokedewai.data.errors.Error // Your custom Error sealed class
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.contentnegotiation.ContentConverterException
import kotlinx.serialization.SerializationException
import java.io.IOException

/**
 * Converts this [Throwable] to a specific custom [Error] type.
 *
 * @return The corresponding custom [Error].
 */
fun Throwable.toError(): Error {
    return when (this) {
        is ClientRequestException -> Error.HttpError(
            statusCode = this.response.status.value,
            message = "Client error: ${this.response.status.description}",
            cause = this
        )
        is ServerResponseException -> Error.HttpError(
            statusCode = this.response.status.value,
            message = "Server error: ${this.response.status.description}",
            cause = this
        )
        is IOException -> Error.NetworkError(
            message = "Network error: ${this.message}",
            cause = this
        )
        is SerializationException, is ContentConverterException -> Error.SerializationError(
            message = "Serialization error: ${this.message}",
            cause = this
        )
        is Error -> this // If it's already one of our custom errors, return it directly
        else -> Error.UnknownApiError(
            message = "An unknown API error occurred: ${this.message}",
            cause = this
        )
    }
}
