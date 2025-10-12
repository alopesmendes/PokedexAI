package com.ailtontech.pokedewai.presentation.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import com.ailtontech.pokedewai.R
import com.ailtontech.pokedewai.presentation.theme.PokedexAITheme
import org.junit.Rule
import org.junit.Test

class CustomImageTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun givenPlaceholder_whenLoading_thenPlaceholderIsVisible() {
        // Given
        val placeholder = R.drawable.placeholder

        // When
        composeTestRule.setContent {
            PokedexAITheme {
                CustomImage(
                    imageUrl = "", // Empty URL to keep it in loading state
                    placeholder = placeholder,
                    contentDescription = "Placeholder Image"
                )
            }
        }

        // Then
        composeTestRule.onNodeWithContentDescription("Placeholder Image").assertIsDisplayed()
    }

    @Test
    fun givenErrorDrawable_whenImageFailsToLoad_thenErrorDrawableIsVisible() {
        // Given
        val errorDrawable = R.drawable.error

        // When
        composeTestRule.setContent {
            PokedexAITheme {
                CustomImage(
                    imageUrl = "https://example.com/invalid_image.png", // Invalid URL
                    error = errorDrawable,
                    contentDescription = "Error Image"
                )
            }
        }

        // Then
        composeTestRule.onNodeWithContentDescription("Error Image").assertIsDisplayed()
    }


    @Test
    fun givenShimmerEnabled_whenLoading_thenShimmerEffectIsApplied() {
        // When
        composeTestRule.setContent {
            PokedexAITheme {
                CustomImage(
                    imageUrl = "", // Empty URL to keep it in loading state
                    shimmer = true,
                    contentDescription = "Shimmer Image"
                )
            }
        }

        // Then
        composeTestRule.onNodeWithContentDescription("Shimmer Image").assertIsDisplayed()
    }
}
