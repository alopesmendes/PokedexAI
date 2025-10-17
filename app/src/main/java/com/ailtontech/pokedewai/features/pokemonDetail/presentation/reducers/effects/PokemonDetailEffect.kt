package com.ailtontech.pokedewai.features.pokemonDetail.presentation.reducers.effects

import androidx.compose.runtime.Immutable
import com.ailtontech.pokedewai.presentation.reducers.IReducer

@Immutable
sealed interface PokemonDetailEffect : IReducer.IEffect {
    data object NavigateBack : PokemonDetailEffect
}