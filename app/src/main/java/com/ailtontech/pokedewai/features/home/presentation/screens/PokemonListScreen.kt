package com.ailtontech.pokedewai.features.home.presentation.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ailtontech.pokedewai.features.home.presentation.components.PokemonContent
import com.ailtontech.pokedewai.features.home.presentation.viewModels.PokemonsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PokemonListScreen(
    modifier: Modifier = Modifier,
    viewModel: PokemonsViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    PokemonContent(
        state = state,
        modifier = modifier,
        sendEvent = viewModel::sendEvent,
    )
}