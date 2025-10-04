package com.ailtontech.pokedewai.features.home.data.datasources.remote

import com.ailtontech.pokedewai.features.home.data.models.PokemonListDto

/**
 * Interface for fetching Pokemon list data from a remote source.
 */
interface IPokemonListRemoteDatasource {
    /**
     * Fetches a paginated list of Pokemon.
     *
     * @param offset The starting index of the list.
     * @param limit The maximum number of items to return.
     * @return A [PokemonListDto] containing the list of Pokemon and pagination info.
     * @throws com.ailtontech.pokedewai.data.errors.Error for API or network issues.
     */
    suspend fun getPokemonList(offset: Int = 0, limit: Int = 20): PokemonListDto
}
