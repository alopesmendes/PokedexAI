package com.ailtontech.pokedewai.features.home.presentation.models

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Immutable
data class PokemonStatus(
    val icon: ImageVector,
    val tint: Color,
    val contentDescription: String? = null,
)
