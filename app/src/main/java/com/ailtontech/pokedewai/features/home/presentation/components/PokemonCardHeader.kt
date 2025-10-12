package com.ailtontech.pokedewai.features.home.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ailtontech.pokedewai.features.home.presentation.models.PokemonCardModel
import com.ailtontech.pokedewai.features.home.presentation.models.PokemonStatus
import com.ailtontech.pokedewai.presentation.components.CustomImage
import com.ailtontech.pokedewai.presentation.theme.LocalDimensions

@Composable
fun PokemonCardHeader(
    model: PokemonCardModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        CustomImage(
            imageUrl = model.imageUrl,
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        topStart = LocalDimensions.current.paddingSmall,
                        topEnd = LocalDimensions.current.paddingSmall
                    )
                ),
            contentScale = ContentScale.Crop
        )
        HorizontalDivider()
    }
}

@Preview
@Composable
fun PokemonCardHeaderPreview() {
    val model = PokemonCardModel(
        id = 1,
        name = "bulbasaur",
        imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png",
        types = emptyList(),
        status = listOf(
            PokemonStatus(
                icon = ImageVector.Builder(
                    name = "dummy",
                    defaultWidth = 24.dp,
                    defaultHeight = 24.dp,
                    viewportWidth = 24f,
                    viewportHeight = 24f
                ).build(), tint = Color.Red, contentDescription = "status"
            )
        ),
        isBattleOnly = false,
        isDefault = true,
        isMega = false
    )
    PokemonCardHeader(model = model)
}