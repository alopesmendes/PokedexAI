package com.ailtontech.pokedewai.features.pokemonDetail.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PokemonAboutSection() {
    PokemonInfoSection(title = "About") {
        Row(horizontalArrangement = Arrangement.SpaceAround) {
            PokemonInfoItem(label = "Species", value = "Mouse Pokemon")
            PokemonInfoItem(label = "Height", value = "0.4m")
        }
        Row(horizontalArrangement = Arrangement.SpaceAround) {
            PokemonInfoItem(label = "Weight", value = "6.0kg")
            PokemonInfoItem(label = "Abilities", value = "Static")
        }
    }
}

@Preview
@Composable
private fun PokemonAboutSectionPreview() {
    PokemonAboutSection()
}
