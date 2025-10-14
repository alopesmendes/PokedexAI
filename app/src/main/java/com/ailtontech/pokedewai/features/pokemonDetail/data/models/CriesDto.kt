package com.ailtontech.pokedewai.features.pokemonDetail.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CriesDto(
    @SerialName("latest")
    val latest: String,
    @SerialName("legacy")
    val legacy: String
)
