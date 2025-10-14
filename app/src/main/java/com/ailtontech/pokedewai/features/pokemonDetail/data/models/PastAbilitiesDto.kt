package com.ailtontech.pokedewai.features.pokemonDetail.data.models

import com.ailtontech.pokedewai.data.models.NamedAPIResourceDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PastAbilitiesDto(
    @SerialName("abilities")
    val abilities: List<AbilitiesDto>,
    @SerialName("generation")
    val generation: NamedAPIResourceDto
)
