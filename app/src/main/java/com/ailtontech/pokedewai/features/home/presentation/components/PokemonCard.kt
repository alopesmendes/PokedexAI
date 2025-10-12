package com.ailtontech.pokedewai.features.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dangerous
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Card
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.ailtontech.pokedewai.features.home.presentation.models.PokemonCardModel
import com.ailtontech.pokedewai.features.home.presentation.models.PokemonStatus
import com.ailtontech.pokedewai.presentation.models.PokemonType
import com.ailtontech.pokedewai.presentation.theme.LocalDimensions
import com.ailtontech.pokedewai.presentation.theme.LocalWindowSize
import com.ailtontech.pokedewai.presentation.theme.PokedexAITheme

/**
 * A composable that displays a summary of a Pokémon, including its image, name, types, and special statuses.
 *
 * @param model The [PokemonCardModel] containing the data to display.
 * @param modifier The modifier to be applied to the card.
 * @param onClick A callback invoked when the card is clicked.
 */
@Composable
fun PokemonCard(
    model: PokemonCardModel,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier,
        onClick = onClick,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(LocalDimensions.current.spacingSmall),
        ) {
            PokemonCardHeader(model)

            PokemonCardContent(
                model,
                modifier = Modifier.padding(horizontal = LocalDimensions.current.paddingSmall)
            )
        }
    }
}

private class PokemonCardModelProvider : PreviewParameterProvider<PokemonCardModel> {
    override val values = sequenceOf(
        PokemonCardModel(
            id = 1,
            name = "bulbasaur",
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
            types = listOf(PokemonType.GRASS, PokemonType.POISON),
            isBattleOnly = false,
            isDefault = true,
            isMega = false,
            status = emptyList()
        ),
        PokemonCardModel(
            id = 6,
            name = "charizard",
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/6.png",
            types = listOf(
                PokemonType.FIRE,
                PokemonType.FLYING,
                PokemonType.POISON,
                PokemonType.PSYCHIC
            ),
            isBattleOnly = true,
            isDefault = false,
            isMega = false,
            status = listOf(
                PokemonStatus(
                    icon = Icons.Filled.Dangerous,
                    tint = Color.Red,
                ),
            )

        ),
        PokemonCardModel(
            id = 150,
            name = "mewtwo",
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/150.png",
            types = listOf(PokemonType.PSYCHIC),
            isBattleOnly = true,
            isDefault = true,
            isMega = true,
            status = listOf(
                PokemonStatus(
                    icon = Icons.Filled.Dangerous,
                    tint = Color.Red,
                ),
                PokemonStatus(
                    icon = Icons.Filled.Stars,
                    tint = Color.Magenta,
                )
            )
        )
    )
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Preview
@Composable
private fun PokemonCardPreview(
    @PreviewParameter(PokemonCardModelProvider::class) model: PokemonCardModel
) {
    CompositionLocalProvider(
        LocalWindowSize provides WindowSizeClass.calculateFromSize(DpSize(200.dp, 400.dp))
    ) {
        PokedexAITheme {
            PokemonCard(model = model, onClick = {})
        }
    }
}
