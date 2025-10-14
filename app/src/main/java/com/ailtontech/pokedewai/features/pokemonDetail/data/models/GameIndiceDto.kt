package com.ailtontech.pokedewai.features.pokemonDetail.data.models

import com.ailtontech.pokedewai.data.models.NamedAPIResourceDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GameIndiceDto(
    @SerialName("game_index")
    val gameIndex: Int,
    @SerialName("version")
    val version: NamedAPIResourceDto
)
