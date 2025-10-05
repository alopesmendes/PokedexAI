package com.ailtontech.pokedewai.features.home.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.ailtontech.pokedewai.features.home.presentation.viewModels.PokemonsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PokemonListScreen(
    modifier: Modifier = Modifier,
    viewModel: PokemonsViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = modifier,
    ) {
        items(state.pokemons, key = { it.id }) {
            ListItem(
                headlineContent = { Text(it.name) },
                leadingContent = {
                    AsyncImage(
                        model = it.imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.Cyan)
                    )
                }
            )
        }
    }
}