package com.ailtontech.pokedewai.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.ailtontech.pokedewai.presentation.theme.LocalDimensions

@Composable
fun StatusIcon(
    icon: ImageVector,
    tint: Color,
    modifier: Modifier = Modifier,
    contentDescription: String? = null
) {
    Icon(
        imageVector = icon,
        contentDescription = contentDescription,
        tint = tint,
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.background.copy(alpha = 0.5f),
                shape = MaterialTheme.shapes.small
            )
            .padding(LocalDimensions.current.paddingExtraSmall)
    )
}

@Preview
@Composable
fun StatusIconPreview() {
    StatusIcon(
        icon = Icons.Default.Warning,
        tint = Color.Red,
        contentDescription = "Warning"
    )
}