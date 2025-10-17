package com.ailtontech.pokedewai.features.pokemonDetail.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PokemonInfoSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = title)
        content()
    }
}

@Preview
@Composable
private fun PokemonInfoSectionPreview() {
    PokemonInfoSection(title = "About") {
        Row(horizontalArrangement = Arrangement.SpaceAround) {
            PokemonInfoItem(label = "Species", value = "Mouse Pokemon")
            PokemonInfoItem(label = "Height", value = "0.4m")
        }
    }
}
