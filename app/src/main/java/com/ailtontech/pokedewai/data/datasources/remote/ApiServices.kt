package com.ailtontech.pokedewai.data.datasources.remote

import com.ailtontech.pokedewai.data.errors.Error
import com.ailtontech.pokedewai.data.mappers.toError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * A singleton object that executes Ktor API calls, handling exceptions
 * by throwing custom [Error] types.
 */
object ApiServices {

    /**
     * Executes a Ktor API call and handles exceptions by throwing custom [Error] types.
     *
     * @param T The type of the expected successful response.
     * @param apiCall The suspend lambda function representing the Ktor API call.
     * @param dispatcher The coroutine dispatcher for the API call.
     * @return The result of type [T] if the API call is successful.
     * @throws Error if any error occurs during the API call, mapped via [toError] extension.
     */
    suspend fun <T> execute(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): T =
        withContext(dispatcher) {
            try {
                return@withContext apiCall()
            } catch (e: Throwable) {
                throw e.toError()
            }
        }
}
