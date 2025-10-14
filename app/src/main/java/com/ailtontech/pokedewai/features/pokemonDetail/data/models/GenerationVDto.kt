package com.ailtontech.pokedewai.features.pokemonDetail.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenerationVDto(
    @SerialName("black-white")
    val blackWhite: BlackWhiteDto
)
