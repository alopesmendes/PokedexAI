package com.ailtontech.pokedewai.features.pokemonDetail.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.ailtontech.pokedewai.R

@Composable
fun PokemonLocationSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Location")
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "Location",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.7f),
            contentScale = ContentScale.Crop
        )
    }
}

@Preview
@Composable
private fun PokemonLocationSectionPreview() {
    PokemonLocationSection()
}
