package com.ailtontech.pokedewai.features.pokemonDetail.data.models

import com.ailtontech.pokedewai.data.models.NamedAPIResourceDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoveDto(
    @SerialName("move")
    val move: NamedAPIResourceDto,
    @SerialName("version_group_details")
    val versionGroupDetails: List<VersionGroupDetailDto>
)
