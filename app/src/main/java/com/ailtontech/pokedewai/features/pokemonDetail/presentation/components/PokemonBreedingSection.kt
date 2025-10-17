package com.ailtontech.pokedewai.features.pokemonDetail.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PokemonBreedingSection() {
    PokemonInfoSection(title = "Breeding") {
        Row(horizontalArrangement = Arrangement.SpaceAround) {
            PokemonInfoItem(label = "Gender", value = "Male, Female")
            PokemonInfoItem(label = "Egg Groups", value = "Field, Fairy")
        }
        PokemonInfoItem(label = "Egg Cycle", value = "10")
    }
}

@Preview
@Composable
private fun PokemonBreedingSectionPreview() {
    PokemonBreedingSection()
}
