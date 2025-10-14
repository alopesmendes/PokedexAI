package com.ailtontech.pokedewai.features.pokemonDetail.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenerationIIIDto(
    @SerialName("emerald")
    val emerald: EmeraldDto,
    @SerialName("firered-leafgreen")
    val fireredLeafgreen: FireredLeafgreenDto,
    @SerialName("ruby-sapphire")
    val rubySapphire: RubySapphireDto
)
