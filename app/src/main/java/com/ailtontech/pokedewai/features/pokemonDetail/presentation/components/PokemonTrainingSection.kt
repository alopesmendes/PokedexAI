package com.ailtontech.pokedewai.features.pokemonDetail.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PokemonTrainingSection() {
    PokemonInfoSection(title = "Training") {
        Row(horizontalArrangement = Arrangement.SpaceAround) {
            PokemonInfoItem(label = "Base EXP", value = "112")
            PokemonInfoItem(label = "Catch Rate", value = "190")
        }
        Row(horizontalArrangement = Arrangement.SpaceAround) {
            PokemonInfoItem(label = "Base Friendship", value = "70")
            PokemonInfoItem(label = "Base Stats", value = "320")
        }
    }
}

@Preview
@Composable
private fun PokemonTrainingSectionPreview() {
    PokemonTrainingSection()
}
