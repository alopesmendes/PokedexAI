package com.ailtontech.pokedewai.presentation.navigation

import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.ailtontech.pokedewai.features.home.presentation.screens.PokemonListScreen

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
            entry<NavRoutes.Search> {

            }
            entry<NavRoutes.Locations> {

            }
            entry<NavRoutes.Settings> {

            }
            entry<RoutesDetail.PokemonDetail>(
                metadata = ListDetailSceneStrategy.detailPane()
            ) {
                Text("id ${it.id}")
            }
        }
    )
}