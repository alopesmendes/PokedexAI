package com.ailtontech.pokedewai.utils

object Tools {
    /**
     * Extracts the Pokémon ID from a PokéAPI URL.
     *
     * The PokéAPI resource URLs typically end with '/{id}/'. This function parses the URL
     * to retrieve that ID.
     *
     * Example:
     * For the URL "https://pokeapi.co/api/v2/pokemon/25/", this function will return 25.
     *
     * @param url The full URL of the Pokémon resource.
     * @return The integer ID of the Pokémon.
     */
    fun extractPokemonIdFromUrl(url: String): Int {
        return url.trimEnd('/').split('/').last().toInt()
    }

    /**
     * Extracts a specific value from a URL query parameter.
     *
     * This function takes a full URL string and the name of a query parameter,
     * and returns the value associated with that parameter. For example, given the URL
     * "https://pokeapi.co/api/v2/pokemon?limit=151&offset=0" and the key "limit",
     * it would return "151".
     *
     * @param url The full URL string containing the query parameters.
     * @param queryParam The name of the query parameter from which to extract the value.
     * @return The value of the specified query parameter as a [String], or `null` if the key is not found.
     */
    fun extractValueFromQueryParam(url: String, queryParam: String): String {
        return url.substringAfter('?')
            .split('&')
            .firstOrNull { it.startsWith("$queryParam=") }
            ?.substringAfter('=') ?: ""
    }
}