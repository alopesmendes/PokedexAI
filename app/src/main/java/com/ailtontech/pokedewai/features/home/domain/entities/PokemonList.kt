package com.ailtontech.pokedewai.features.home.domain.entities

data class PokemonList(
    val count: Int,
    val offset: Int? = 0,
    val limit: Int? = 20,
    val pokemonForms: List<PokemonForm>
)
