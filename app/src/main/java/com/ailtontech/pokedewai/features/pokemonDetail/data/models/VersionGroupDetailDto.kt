package com.ailtontech.pokedewai.features.pokemonDetail.data.models

import com.ailtontech.pokedewai.data.models.NamedAPIResourceDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VersionGroupDetailDto(
    @SerialName("level_learned_at")
    val levelLearnedAt: Int,
    @SerialName("move_learn_method")
    val moveLearnMethod: NamedAPIResourceDto,
    @SerialName("version_group")
    val versionGroup: NamedAPIResourceDto
)
