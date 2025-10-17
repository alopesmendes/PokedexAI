package com.ailtontech.pokedewai.features.pokemonDetail.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PokemonInfoItem(
    label: String,
    value: String
) {
    Column {
        Text(text = label)
        Text(text = value)
    }
}

@Preview
@Composable
private fun PokemonInfoItemPreview() {
    PokemonInfoItem(label = "Species", value = "Mouse Pokemon")
}
