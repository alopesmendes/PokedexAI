package com.ailtontech.pokedewai.features.home.domain.entities

data class PokemonForm(
    val id: Int,
    val name: String,
    val order: Int,
    val imageUrl: String?,
    val types: List<String>,
    val isBattleOnly: Boolean,
    val isDefault: Boolean,
    val isMega: Boolean
)
