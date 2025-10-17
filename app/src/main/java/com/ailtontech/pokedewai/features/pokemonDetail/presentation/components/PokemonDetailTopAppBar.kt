package com.ailtontech.pokedewai.features.pokemonDetail.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailTopAppBar(
    pokemonName: String,
    onBackClicked: () -> Unit
) {
    TopAppBar(
        title = { Text(text = pokemonName) },
        navigationIcon = {
            IconButton(onClick = onBackClicked) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    )
}

@Preview
@Composable
private fun PokemonDetailTopAppBarPreview() {
    PokemonDetailTopAppBar(pokemonName = "Pikachu", onBackClicked = {})
}
