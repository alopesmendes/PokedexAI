package com.ailtontech.pokedewai.features.home.data.mappers

import com.ailtontech.pokedewai.features.home.data.models.PokemonFormDto
import com.ailtontech.pokedewai.features.home.domain.entities.PokemonForm

/**
 * Maps a [PokemonFormDto] from the data layer to a [PokemonForm] entity in the domain layer.
 */
fun PokemonFormDto.toEntity(): PokemonForm {
    return PokemonForm(
        id = this.id,
        name = this.name,
        order = this.order,
        imageUrl = this.sprites.frontDefault ?: "",
        types = this.types.mapNotNull { it.type?.name },
        isBattleOnly = this.isBattleOnly,
        isDefault = this.isDefault,
        isMega = this.isMega
    )
}
