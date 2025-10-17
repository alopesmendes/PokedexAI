package com.ailtontech.pokedewai.features.pokemonDetail.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun PokemonDetailScreen(
    pokemonName: String,
    imageUrl: String,
    onBackClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            PokemonDetailTopAppBar(
                pokemonName = pokemonName,
                onBackClicked = onBackClicked
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            PokemonImageBanner(
                imageUrl = imageUrl,
                contentDescription = pokemonName
            )
            PokemonAboutSection()
            PokemonBreedingSection()
            PokemonLocationSection()
            PokemonTrainingSection()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PokemonDetailScreenPreview() {
    PokemonDetailScreen(
        pokemonName = "Pikachu",
        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/25.png",
        onBackClicked = {}
    )
}
