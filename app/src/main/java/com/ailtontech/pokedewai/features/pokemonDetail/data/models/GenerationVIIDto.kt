package com.ailtontech.pokedewai.features.pokemonDetail.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenerationVIIDto(
    @SerialName("icons")
    val icons: IconsDto,
    @SerialName("ultra-sun-ultra-moon")
    val ultraSunUltraMoon: UltraSunUltraMoonDto
)
