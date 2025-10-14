package com.ailtontech.pokedewai.features.pokemonDetail.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenerationIDto(
    @SerialName("red-blue")
    val redBlue: RedBlueDto,
    @SerialName("yellow")
    val yellow: YellowDto
)
