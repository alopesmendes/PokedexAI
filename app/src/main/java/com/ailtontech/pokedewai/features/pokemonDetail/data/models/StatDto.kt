package com.ailtontech.pokedewai.features.pokemonDetail.data.models

import com.ailtontech.pokedewai.data.models.NamedAPIResourceDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatDto(
    @SerialName("base_stat")
    val baseStat: Int,
    @SerialName("effort")
    val effort: Int,
    @SerialName("stat")
    val stat: NamedAPIResourceDto
)
