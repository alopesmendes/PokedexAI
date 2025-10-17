package com.ailtontech.pokedewai.features.pokemonDetail.presentation.viewModels

import com.ailtontech.pokedewai.features.pokemonDetail.presentation.reducers.PokemonDetailReducer
import com.ailtontech.pokedewai.features.pokemonDetail.presentation.reducers.PokemonDetailState
import com.ailtontech.pokedewai.features.pokemonDetail.presentation.reducers.effects.PokemonDetailEffect
import com.ailtontech.pokedewai.features.pokemonDetail.presentation.reducers.events.PokemonDetailEvent
import com.ailtontech.pokedewai.presentation.viewModels.BaseViewModel

class PokemonDetailViewModel(
    reducer: PokemonDetailReducer,
) : BaseViewModel<PokemonDetailState, PokemonDetailEvent, PokemonDetailEffect>(
    reducer = reducer,
    initialState = PokemonDetailState()
) {

    fun getPokemon(name: String) {
        sendEvent(PokemonDetailEvent.Loading)
        sendEvent(PokemonDetailEvent.GetPokemonDetail(name))
    }
}
