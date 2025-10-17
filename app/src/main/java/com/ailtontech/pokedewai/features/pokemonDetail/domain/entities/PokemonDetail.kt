package com.ailtontech.pokedewai.features.pokemonDetail.domain.entities

data class PokemonDetail(
    val id: Int,
    val isDefault: Boolean,
    val order: Int,
    val name: String,
    val weight: Int,
    val abilities: List<Abilities>,
    val species: String,
    val stats: List<Stat>,
    val types: List<Type>,
    val moves: List<Move>,
    val sprites: Sprites,
)
