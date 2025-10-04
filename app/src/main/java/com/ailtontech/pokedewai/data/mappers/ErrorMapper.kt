package com.ailtontech.pokedewai.data.mappers

import com.ailtontech.pokedewai.data.errors.Error
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.contentnegotiation.ContentConverterException
import io.ktor.serialization.JsonConvertException
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
            statusCode = response.status.value,
            message = "Client request failed: ${response.status.description}",
            cause = this
        )
        is ServerResponseException -> Error.HttpError(
            statusCode = response.status.value,
            message = "Server response error: ${response.status.description}",
            cause = this
        )
        is NoTransformationFoundException -> Error.HttpError(
            statusCode = 404,
            message = "Failed to transform response body: ${this.message}",
            cause = this
        )
        is ResponseException -> Error.HttpError(
            statusCode = response.status.value,
            message = "HTTP error: ${response.status.description}",
            cause = this
        )
        is IOException -> Error.NetworkError(
            message = "Network error: ${this.message}",
            cause = this
        )
        is SerializationException, is ContentConverterException, is JsonConvertException -> Error.SerializationError(
            message = "Serialization error: ${this.message}",
            cause = this
        )
        is Error -> this
        else -> Error.UnknownApiError(
            message = "An unknown API error occurred: ${this.message}",
            cause = this
        )
    }
}
