package com.ailtontech.pokedewai.features.pokemonDetail.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenerationVIIIDto(
    @SerialName("icons")
    val icons: IconsDto
)
