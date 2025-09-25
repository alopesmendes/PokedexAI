package com.ailtontech.pokedewai.utils

object Constants {
    const val BASE_URL = "https://pokeapi.co/api/v2/"

    const val REQUEST_TIMEOUT_MILLIS = 30000L // 30 seconds
    const val CONNECT_TIMEOUT_MILLIS = 30000L // 30 seconds
    const val SOCKET_TIMEOUT_MILLIS = 30000L // 30 seconds

    const val MAX_RETRIES = 3
    const val RETRY_REQUEST_DELAY = 1000L // 1 second
}
