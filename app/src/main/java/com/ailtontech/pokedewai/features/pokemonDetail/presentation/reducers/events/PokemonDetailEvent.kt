package com.ailtontech.pokedewai.features.pokemonDetail.presentation.reducers.events

import androidx.compose.runtime.Immutable
import com.ailtontech.pokedewai.presentation.reducers.IReducer

/**
 * Defines the events that can be dispatched in the PokemonDetail feature.
 */
@Immutable
sealed interface PokemonDetailEvent : IReducer.IEvent {
    /**
     * Indicates that the pokemon detail is being loaded.
     */
    data object Loading : PokemonDetailEvent

    /**
     * Triggers the fetching of a pokemon's detail.
     *
     * @param name The name of the pokemon to fetch.
     */
    data class GetPokemonDetail(val name: String) : PokemonDetailEvent
}
