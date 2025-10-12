package com.ailtontech.pokedewai.features.home.presentation.mappers

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dangerous
import androidx.compose.material.icons.filled.Stars
import androidx.compose.ui.graphics.Color
import com.ailtontech.pokedewai.features.home.domain.entities.PokemonForm
import com.ailtontech.pokedewai.features.home.presentation.models.PokemonCardModel
import com.ailtontech.pokedewai.features.home.presentation.models.PokemonStatus
import com.ailtontech.pokedewai.presentation.models.PokemonType
import java.util.Locale

fun PokemonForm.toCardModel(): PokemonCardModel {
    return PokemonCardModel(
        id = this.id,
        name = this.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
        imageUrl = this.imageUrl,
        types = this.types.map { PokemonType.fromString(it) },
        isBattleOnly = this.isBattleOnly,
        isDefault = this.isDefault,
        isMega = this.isMega,
        status = buildList {
            if (isBattleOnly) add(
                PokemonStatus(
                    icon = Icons.Filled.Dangerous,
                    tint = Color.Red,
                )
            )
            if (isMega) add(
                PokemonStatus(
                    icon = Icons.Filled.Stars,
                    tint = Color.Magenta,
                )
            )
        }
    )
}
