package com.ailtontech.pokedewai.features.pokemonDetail.data.models

import com.ailtontech.pokedewai.data.models.NamedAPIResourceDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VersionDetailDto(
    @SerialName("rarity")
    val rarity: Int,
    @SerialName("version")
    val version: NamedAPIResourceDto
)
