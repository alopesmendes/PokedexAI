package com.ailtontech.pokedewai.features.pokemonDetail.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter

@Composable
fun PokemonImageBanner(
    imageUrl: String,
    contentDescription: String?
) {
    Image(
        painter = rememberAsyncImagePainter(imageUrl),
        contentDescription = contentDescription,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.5f),
        contentScale = ContentScale.Crop
    )
}

@Preview
@Composable
private fun PokemonImageBannerPreview() {
    PokemonImageBanner(
        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/25.png",
        contentDescription = "Pikachu"
    )
}
