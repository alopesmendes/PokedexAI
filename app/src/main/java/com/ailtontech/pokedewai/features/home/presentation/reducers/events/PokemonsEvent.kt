package com.ailtontech.pokedewai.features.home.presentation.reducers.events

import androidx.compose.runtime.Immutable
import com.ailtontech.pokedewai.presentation.reducers.IReducer

@Immutable
sealed interface PokemonsEvent : IReducer.IEvent {
    data object PokemonsListLoading : PokemonsEvent

    data object GetPokemonsList : PokemonsEvent

    data class GetPokemonDetail(
        val id: Int
    ) : PokemonsEvent
}