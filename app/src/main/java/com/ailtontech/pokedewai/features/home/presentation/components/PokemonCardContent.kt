package com.ailtontech.pokedewai.features.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.ailtontech.pokedewai.features.home.presentation.models.PokemonCardModel
import com.ailtontech.pokedewai.features.home.presentation.models.PokemonStatus
import com.ailtontech.pokedewai.presentation.components.StatusIcon
import com.ailtontech.pokedewai.presentation.components.Tag
import com.ailtontech.pokedewai.presentation.models.PokemonType
import com.ailtontech.pokedewai.presentation.theme.LocalDimensions

@Composable
fun PokemonCardContent(
    model: PokemonCardModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = LocalDimensions.current.paddingSmall),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(LocalDimensions.current.spacingMedium)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                buildAnnotatedString {
                    pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                    append("#")
                    pop()
                    pushStyle(SpanStyle(color = MaterialTheme.colorScheme.outline))
                    append(
                        text = model.id.toString(),
                    )
                    pop()
                },
                style = MaterialTheme.typography.labelMedium
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(LocalDimensions.current.spacingExtraSmall),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                model.status.forEach { status ->
                    StatusIcon(
                        icon = status.icon,
                        tint = status.tint,
                        contentDescription = status.contentDescription
                    )
                }
            }
        }

        Text(
            text = model.name,
            style = MaterialTheme.typography.titleLarge
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(LocalDimensions.current.spacingSmall),
            verticalArrangement = Arrangement.spacedBy(LocalDimensions.current.spacingSmall),
        ) {
            model.types.forEach { type ->
                Tag(
                    text = type.name,
                    color = type.color
                )
            }
        }
    }
}

@Preview
@Composable
fun PokemonCardContentPreview() {
    val model = PokemonCardModel(
        id = 1,
        name = "Bulbasaur",
        imageUrl = null,
        types = listOf(
            PokemonType.PSYCHIC,
            PokemonType.POISON,
        ),
        status = listOf(
            PokemonStatus(
                icon = Icons.Default.Star,
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = "Favorite"
            )
        ),
        isBattleOnly = false,
        isDefault = true,
        isMega = false
    )
    PokemonCardContent(model = model)
}