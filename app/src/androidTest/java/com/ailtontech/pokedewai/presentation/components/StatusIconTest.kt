package com.ailtontech.pokedewai.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import com.ailtontech.pokedewai.presentation.theme.PokedexAITheme
import org.junit.Rule
import org.junit.Test

class StatusIconTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenIconAndTint_whenDisplayed_thenIconIsVisible() {
        // Given
        val icon = Icons.Default.CheckCircle
        val tint = Color.Green

        // When
        composeTestRule.setContent {
            PokedexAITheme {
                StatusIcon(
                    icon = icon,
                    tint = tint,
                    contentDescription = "Test Icon"
                )
            }
        }

        // Then
        composeTestRule.onNodeWithContentDescription("Test Icon").assertIsDisplayed()
    }

    @Test
    fun givenContentDescription_whenDisplayed_thenIsSetCorrectly() {
        // Given
        val contentDescription = "Verified Status"

        // When
        composeTestRule.setContent {
            PokedexAITheme {
                StatusIcon(
                    icon = Icons.Default.CheckCircle,
                    tint = Color.Green,
                    contentDescription = contentDescription
                )
            }
        }

        // Then
        composeTestRule.onNodeWithContentDescription(contentDescription).assertExists()
    }
}
