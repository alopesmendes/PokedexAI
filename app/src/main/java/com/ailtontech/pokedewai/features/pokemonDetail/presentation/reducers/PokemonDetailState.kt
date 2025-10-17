package com.ailtontech.pokedewai.features.pokemonDetail.presentation.reducers

import androidx.compose.runtime.Immutable
import com.ailtontech.pokedewai.features.pokemonDetail.domain.entities.PokemonDetail
import com.ailtontech.pokedewai.features.pokemonDetail.domain.failures.PokemonDetailFailure
import com.ailtontech.pokedewai.presentation.reducers.IReducer

@Immutable
data class PokemonDetailState(
    val isLoading: Boolean = false,
    val pokemon: PokemonDetail? = null,
    val failure: PokemonDetailFailure? = null,
) : IReducer.IState