package com.ailtontech.pokedewai.features.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.ailtontech.pokedewai.features.home.presentation.models.PokemonCardModel
import com.ailtontech.pokedewai.features.home.presentation.reducers.PokemonsState
import com.ailtontech.pokedewai.features.home.presentation.reducers.events.PokemonsEvent
import com.ailtontech.pokedewai.presentation.models.PokemonType
import com.ailtontech.pokedewai.presentation.theme.LocalDimensions
import com.ailtontech.pokedewai.presentation.theme.LocalWindowSize
import com.ailtontech.pokedewai.presentation.theme.PokedexAITheme

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun PokemonContent(
    state: PokemonsState,
    modifier: Modifier = Modifier,
    sendEvent: (PokemonsEvent) -> Unit,
) {
    val listState = rememberLazyListState()
    val gridState = rememberLazyGridState()

    val isCompact = LocalWindowSize.current.widthSizeClass == WindowWidthSizeClass.Compact

    if (isCompact) {
        LazyVerticalGrid(
            state = gridState,
            columns = GridCells.Fixed(2),
            modifier = modifier,
            contentPadding = PaddingValues(
                vertical = LocalDimensions.current.paddingSmall,
                horizontal = LocalDimensions.current.paddingMedium
            ),
            verticalArrangement = Arrangement.spacedBy(LocalDimensions.current.spacingSmall),
            horizontalArrangement = Arrangement.spacedBy(LocalDimensions.current.spacingSmall)
        ) {
            items(state.pokemons, key = { it.id }) { model ->
                PokemonCard(model = model) {
                    sendEvent(PokemonsEvent.GetPokemonDetail(model.id))
                }
            }
            if (state.isLoading) {
                item(span = { GridItemSpan(2) }) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(LocalDimensions.current.paddingMedium),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    } else {
        LazyColumn(
            state = listState,
            modifier = modifier,
            contentPadding = PaddingValues(
                vertical = LocalDimensions.current.paddingSmall,
                horizontal = LocalDimensions.current.paddingMedium
            ),
            verticalArrangement = Arrangement.spacedBy(LocalDimensions.current.spacingSmall)
        ) {
            items(state.pokemons, key = { it.id }) { model ->
                PokemonCard(model = model) {
                    sendEvent(PokemonsEvent.GetPokemonDetail(model.id))
                }
            }
            if (state.isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(LocalDimensions.current.paddingMedium),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadingIndicator()
                    }
                }
            }
        }
    }

    val endOfListReached by remember {
        derivedStateOf {
            if (isCompact) {
                val layoutInfo = gridState.layoutInfo
                val visibleItemsInfo = layoutInfo.visibleItemsInfo
                if (layoutInfo.totalItemsCount == 0) {
                    false
                } else {
                    visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
                }
            } else {
                val layoutInfo = listState.layoutInfo
                val visibleItemsInfo = layoutInfo.visibleItemsInfo
                if (layoutInfo.totalItemsCount == 0) {
                    false
                } else {
                    visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
                }
            }
        }
    }

    LaunchedEffect(endOfListReached) {
        if (endOfListReached && !state.isLoading) {
            sendEvent(PokemonsEvent.GetPokemonsList)
        }
    }
}

private class PokemonContentStateProvider : PreviewParameterProvider<PokemonsState> {
    override val values = sequenceOf(
        PokemonsState(
            pokemons = emptyList(),
            isLoading = true
        ),
        PokemonsState(
            pokemons = listOf(
                PokemonCardModel(
                    id = 1,
                    name = "bulbasaur",
                    imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
                    types = listOf(PokemonType.GRASS, PokemonType.POISON),
                    status = emptyList(),
                    isBattleOnly = false,
                    isDefault = true,
                    isMega = false
                ),
                PokemonCardModel(
                    id = 4,
                    name = "charmander",
                    imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/4.png",
                    types = listOf(PokemonType.FIRE),
                    status = emptyList(),
                    isBattleOnly = false,
                    isDefault = true,
                    isMega = false
                ),
                PokemonCardModel(
                    id = 7,
                    name = "squirtle",
                    imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/7.png",
                    types = listOf(PokemonType.WATER),
                    status = emptyList(),
                    isBattleOnly = false,
                    isDefault = true,
                    isMega = false
                )
            ),
            isLoading = false
        ),
        PokemonsState(
            pokemons = listOf(
                PokemonCardModel(
                    id = 1,
                    name = "bulbasaur",
                    imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
                    types = listOf(PokemonType.GRASS, PokemonType.POISON),
                    status = emptyList(),
                    isBattleOnly = false,
                    isDefault = true,
                    isMega = false
                ),
                PokemonCardModel(
                    id = 4,
                    name = "charmander",
                    imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/4.png",
                    types = listOf(PokemonType.FIRE),
                    status = emptyList(),
                    isBattleOnly = false,
                    isDefault = true,
                    isMega = false
                )
            ),
            isLoading = true
        )
    )
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview
@Composable
private fun PokemonContentPreview(
    @PreviewParameter(PokemonContentStateProvider::class) state: PokemonsState
) {
    CompositionLocalProvider(
        LocalWindowSize provides WindowSizeClass.calculateFromSize(DpSize(400.dp, 800.dp))
    ) {
        PokedexAITheme {
            PokemonContent(state = state, sendEvent = {})
        }
    }
}
