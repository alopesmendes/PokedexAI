package com.ailtontech.pokedewai.features.pokemonDetail.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IconsDto(
    @SerialName("front_default")
    val frontDefault: String?,
    @SerialName("front_female")
    val frontFemale: String?
)
