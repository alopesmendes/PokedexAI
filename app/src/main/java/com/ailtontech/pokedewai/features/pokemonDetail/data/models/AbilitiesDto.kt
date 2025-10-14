package com.ailtontech.pokedewai.features.pokemonDetail.data.models

import com.ailtontech.pokedewai.data.models.NamedAPIResourceDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AbilitiesDto(
    @SerialName("ability")
    val ability: NamedAPIResourceDto?,
    @SerialName("is_hidden")
    val isHidden: Boolean,
    @SerialName("slot")
    val slot: Int
)
