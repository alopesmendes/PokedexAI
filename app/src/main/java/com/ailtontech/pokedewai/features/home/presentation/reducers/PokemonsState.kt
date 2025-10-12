package com.ailtontech.pokedewai.features.home.presentation.reducers

import androidx.compose.runtime.Immutable
import com.ailtontech.pokedewai.features.home.failures.PokemonsFailure
import com.ailtontech.pokedewai.features.home.presentation.models.PokemonCardModel
import com.ailtontech.pokedewai.presentation.reducers.IReducer

@Immutable
data class PokemonsState(
    val isLoading: Boolean = false,
    val pokemons: List<PokemonCardModel> = emptyList(),
    val offset: Int? = 0,
    val limit: Int? = 20,
    val count: Int? = null,
    val failure: PokemonsFailure? = null,
) : IReducer.IState
