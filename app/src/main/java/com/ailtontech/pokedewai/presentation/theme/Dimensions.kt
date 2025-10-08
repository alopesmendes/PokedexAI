package com.ailtontech.pokedewai.presentation.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AppDimensions(
    val paddingSmall: Dp,
    val paddingMedium: Dp,
    val paddingLarge: Dp,
    val spacingSmall: Dp,
    val spacingMedium: Dp,
    val spacingLarge: Dp,
    val iconSizeSmall: Dp,
    val iconSizeMedium: Dp,
    val iconSizeLarge: Dp
)

val CompactDimensions = AppDimensions(
    paddingSmall = 4.dp,
    paddingMedium = 8.dp,
    paddingLarge = 16.dp,
    spacingSmall = 4.dp,
    spacingMedium = 8.dp,
    spacingLarge = 16.dp,
    iconSizeSmall = 16.dp,
    iconSizeMedium = 24.dp,
    iconSizeLarge = 32.dp
)

val MediumDimensions = AppDimensions(
    paddingSmall = 8.dp,
    paddingMedium = 16.dp,
    paddingLarge = 24.dp,
    spacingSmall = 8.dp,
    spacingMedium = 16.dp,
    spacingLarge = 24.dp,
    iconSizeSmall = 24.dp,
    iconSizeMedium = 32.dp,
    iconSizeLarge = 48.dp
)

val ExpandedDimensions = AppDimensions(
    paddingSmall = 12.dp,
    paddingMedium = 20.dp,
    paddingLarge = 32.dp,
    spacingSmall = 12.dp,
    spacingMedium = 20.dp,
    spacingLarge = 32.dp,
    iconSizeSmall = 32.dp,
    iconSizeMedium = 48.dp,
    iconSizeLarge = 64.dp
)

val LocalDimensions = compositionLocalOf { CompactDimensions }
