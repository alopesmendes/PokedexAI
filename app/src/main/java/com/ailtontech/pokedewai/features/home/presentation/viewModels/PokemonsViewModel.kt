package com.ailtontech.pokedewai.features.home.presentation.viewModels

import com.ailtontech.pokedewai.features.home.presentation.reducers.PokemonsReducer
import com.ailtontech.pokedewai.features.home.presentation.reducers.PokemonsState
import com.ailtontech.pokedewai.features.home.presentation.reducers.effects.PokemonsEffect
import com.ailtontech.pokedewai.features.home.presentation.reducers.events.PokemonsEvent
import com.ailtontech.pokedewai.presentation.viewModels.BaseViewModel

class PokemonsViewModel(
    reducer: PokemonsReducer,
) : BaseViewModel<PokemonsState, PokemonsEvent, PokemonsEffect>(
    initialState = PokemonsState(),
    reducer = reducer,
) {
    init {
        sendEvent(PokemonsEvent.PokemonsListLoading)
        sendEvent(PokemonsEvent.GetPokemonsList())
    }
}