package com.ailtontech.pokedewai.features.home.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.ailtontech.pokedewai.features.home.presentation.models.PokemonCardModel
import com.ailtontech.pokedewai.features.home.presentation.models.PokemonStatus
import com.ailtontech.pokedewai.presentation.models.PokemonType
import com.ailtontech.pokedewai.presentation.theme.PokedexAITheme
import org.junit.Rule
import org.junit.Test

class PokemonCardContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenPokemonCardModelWhenDisplayedThenShowsAllInformation() {
        // Given
        val model = PokemonCardModel(
            id = 1,
            name = "Bulbasaur",
            imageUrl = null,
            types = listOf(
                PokemonType.GRASS,
                PokemonType.POISON,
            ),
            status = listOf(
                PokemonStatus(
                    icon = Icons.Default.Star,
                    tint = Color.Red,
                    contentDescription = "Favorite"
                )
            ),
            isBattleOnly = false,
            isDefault = true,
            isMega = false
        )

        // When
        composeTestRule.setContent {
            PokedexAITheme {
                PokemonCardContent(model = model)
            }
        }

        // Then
        composeTestRule.onNodeWithText("#1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Bulbasaur").assertIsDisplayed()
        composeTestRule.onNodeWithText("GRASS").assertIsDisplayed()
        composeTestRule.onNodeWithText("POISON").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Favorite").assertIsDisplayed()
    }
}
