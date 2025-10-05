package com.ailtontech.pokedewai.features.home.presentation.reducers.effects

import androidx.compose.runtime.Immutable
import com.ailtontech.pokedewai.presentation.reducers.IReducer

@Immutable
sealed interface PokemonsEffect : IReducer.IEffect {
    data class NavigateToPokemonDetail(
        val id: Int,
    ) : PokemonsEffect
}