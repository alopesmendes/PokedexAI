package com.ailtontech.pokedewai.features.home.presentation.models

import androidx.compose.runtime.Immutable
import com.ailtontech.pokedewai.presentation.models.PokemonType

@Immutable
data class PokemonCardModel(
    val id: Int,
    val name: String,
    val imageUrl: String?,
    val types: List<PokemonType>,
    val status: List<PokemonStatus>,
    val isBattleOnly: Boolean,
    val isDefault: Boolean,
    val isMega: Boolean
)
