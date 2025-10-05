package com.ailtontech.pokedewai.features.home.domain.commands

/**
 * Command to get a paginated list of pokemons.
 *
 * @property count The total number of pokemons.
 * @property offset The starting offset of the pokemon list.
 * @property limit The max number of pokemons to return.
 */
data class PokemonPaginationParams(
    val count: Int,
    val offset: Int,
    val limit: Int,
)
