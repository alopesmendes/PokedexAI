package com.ailtontech.pokedewai.features.home.data.mappers

import com.ailtontech.pokedewai.features.home.data.models.PokemonListDto
import com.ailtontech.pokedewai.features.home.domain.entities.PokemonForm
import com.ailtontech.pokedewai.features.home.domain.entities.PokemonList
import com.ailtontech.pokedewai.utils.Tools

/**
 * Maps a [PokemonListDto] from the data layer to a [PokemonList] entity in the domain layer.
 *
 * This function transforms the raw data transfer object into a more usable domain entity. It calculates
 * the `offset` and `limit` for pagination from the `next` URL and maps the list of Pokemon results
 * into a list of [PokemonForm] entities.
 *
 * @param mapToPokemonForm A lambda function to map a Pokemon ID to its corresponding [PokemonForm].
 *                         (Note: This parameter is currently unused in the function body).
 * @return A [PokemonList] domain entity containing the total count, pagination info, and a list of Pokemon.
 */
fun PokemonListDto.toEntity(mapToPokemonForm: (id: Int) -> PokemonForm?): PokemonList {
    return PokemonList(
        count = this.count,
        offset = next?.let { Tools.extractValueFromQueryParam(it, "offset").toInt() },
        limit = next?.let { Tools.extractValueFromQueryParam(it, "limit").toInt() },
        pokemonForms = this.results.mapNotNull { result ->
            val pokemonId = Tools.extractPokemonIdFromUrl(result.url)
            mapToPokemonForm(pokemonId)
        },
    )
}
