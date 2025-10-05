package com.ailtontech.pokedewai.features.home.data.datasources.remote

import com.ailtontech.pokedewai.features.home.data.models.PokemonFormDto

/**
 * Interface for fetching data about a specific Pokemon Form from the remote API.
 */
interface IPokemonFormRemoteDatasource {

    /**
     * Fetches details for a specific Pokemon Form by its ID.
     *
     * @param name The name of the Pokemon Form.
     * @return A [PokemonFormDto] containing the details of the Pokemon Form.
     * @throws com.ailtontech.pokedewai.data.errors.Error for API, network, or parsing issues.
     */
    suspend fun getPokemonForm(name: String): PokemonFormDto
}
