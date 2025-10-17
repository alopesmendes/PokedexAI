package com.ailtontech.pokedewai.presentation.navigation

import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.ailtontech.pokedewai.features.home.presentation.screens.PokemonListScreen
import com.ailtontech.pokedewai.features.pokemonDetail.presentation.viewModels.PokemonDetailViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
) {
    val backStack = remember { mutableStateListOf<Any>(NavRoutes.Home) }
    val listDetailStrategy = rememberListDetailSceneStrategy<Any>()


    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = { keysToRemove -> repeat(keysToRemove) { backStack.removeLastOrNull() } },
        sceneStrategy = listDetailStrategy,
        entryProvider = entryProvider {
            entry<NavRoutes.Home>(
                metadata = ListDetailSceneStrategy.listPane()
            ) {
                PokemonListScreen()
            }

            entry<RoutesDetail.PokemonDetail>(
                metadata = ListDetailSceneStrategy.detailPane()
            ) {
                val pokemonDetailViewModel = koinViewModel<PokemonDetailViewModel>()

                LaunchedEffect(it.name) {
                    pokemonDetailViewModel.getPokemon(it.name)
                }

                Text("id ${it.id}")
            }
        }
    )
}