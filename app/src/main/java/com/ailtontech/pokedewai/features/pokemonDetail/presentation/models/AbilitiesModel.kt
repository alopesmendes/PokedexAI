package com.ailtontech.pokedewai.features.pokemonDetail.presentation.models

import androidx.compose.runtime.Immutable

@Immutable
data class AbilitiesModel(
    val ability: String?,
    val isHidden: Boolean,
    val slot: Int,
)
