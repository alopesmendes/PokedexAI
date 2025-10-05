package com.ailtontech.pokedewai.data.errors

/**
 * Base sealed class for all custom errors in the data layer.
 * Extends Throwable to be used in Result classes or try/catch blocks.
 */
sealed class Error : Throwable() {
    /**
     * Represents network connectivity issues.
     * @param message A custom message describing the error.
     * @param cause The original throwable that caused this error, if any.
     */
    data class NetworkError(
        override val message: String?,
        override val cause: Throwable? = null
    ) : Error()

    /**
     * Represents HTTP errors from the server (e.g., 4xx, 5xx status codes).
     * @param statusCode The HTTP status code.
     * @param message A custom message describing the error, potentially from the server response.
     * @param cause The original throwable that caused this error (e.g., Ktor's ResponseException).
     */
    data class HttpError(
        val statusCode: Int,
        override val message: String?,
        override val cause: Throwable? = null
    ) : Error()

    /**
     * Represents errors during JSON parsing or data transformation.
     * @param message A custom message describing the serialization error.
     * @param cause The original throwable that caused this error (e.g., kotlinx.serialization.SerializationException).
     */
    data class SerializationError(
        override val message: String?,
        override val cause: Throwable? = null
    ) : Error()

    /**
     * Represents any other unexpected errors during an API call.
     * @param message A custom message describing the unknown error.
     * @param cause The original throwable that caused this error, if any.
     */
    data class UnknownApiError(
        override val message: String?,
        override val cause: Throwable? = null
    ) : Error()
}
