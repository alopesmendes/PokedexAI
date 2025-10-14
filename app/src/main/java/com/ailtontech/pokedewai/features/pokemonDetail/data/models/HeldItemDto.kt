package com.ailtontech.pokedewai.features.pokemonDetail.data.models

import com.ailtontech.pokedewai.data.models.NamedAPIResourceDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HeldItemDto(
    @SerialName("item")
    val item: NamedAPIResourceDto,
    @SerialName("version_details")
    val versionDetails: List<VersionDetailDto>
)
