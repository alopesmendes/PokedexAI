package com.ailtontech.pokedewai.features.pokemonDetail.data.models

import com.ailtontech.pokedewai.data.models.NamedAPIResourceDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TypeDto(
    @SerialName("slot")
    val slot: Int,
    @SerialName("type")
    val type: NamedAPIResourceDto
)
