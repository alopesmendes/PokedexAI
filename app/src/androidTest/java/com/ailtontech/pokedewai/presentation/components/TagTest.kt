package com.ailtontech.pokedewai.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.ailtontech.pokedewai.presentation.theme.PokedexAITheme
import org.junit.Rule
import org.junit.Test

class TagTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenTextAndColor_whenDisplayed_thenShowsUppercaseText() {
        // Given
        val text = "grass"
        val color = Color.Green

        // When
        composeTestRule.setContent {
            PokedexAITheme {
                Tag(text = text, color = color)
            }
        }

        // Then
        composeTestRule.onNodeWithText("GRASS").assertIsDisplayed()
    }

    @Test
    fun givenLeadingIcon_whenDisplayed_thenIconIsVisible() {
        // Given
        val text = "fire"
        val color = Color.Red

        // When
        composeTestRule.setContent {
            PokedexAITheme {
                Tag(
                    text = text,
                    color = color,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Info Icon"
                        )
                    }
                )
            }
        }

        // Then
        composeTestRule.onNodeWithText("FIRE").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Info Icon").assertIsDisplayed()
    }
}
