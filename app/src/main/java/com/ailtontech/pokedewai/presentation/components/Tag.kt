package com.ailtontech.pokedewai.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ailtontech.pokedewai.presentation.model.PokemonType
import com.ailtontech.pokedewai.presentation.theme.PokedexAITheme

/**
 * A composable that displays a colored tag.
 *
 * @param text The text to display inside the tag.
 * @param color The background color of the tag.
 * @param modifier The modifier to be applied to the tag.
 * @param leadingIcon An optional composable to display as a leading icon.
 */
@Composable
fun Tag(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null
) {
    Row(
        modifier = modifier
            .clip(CircleShape)
            .background(color)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (leadingIcon != null) {
            leadingIcon()
            Spacer(modifier = Modifier.width(4.dp))
        }
        Text(
            text = text.uppercase(),
            color = Color.White,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Preview
@Composable
private fun TagPreview() {
    PokedexAITheme {
        Column {
            Tag(
                text = "Grass",
                color = PokemonType.GRASS.color
            )
            Spacer(modifier = Modifier.height(8.dp))
            Tag(
                text = "Fire",
                color = PokemonType.FIRE.color,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(12.dp)
                    )
                }
            )
        }
    }
}
