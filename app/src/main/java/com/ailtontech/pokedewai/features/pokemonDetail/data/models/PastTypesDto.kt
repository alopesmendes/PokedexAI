package com.ailtontech.pokedewai.features.pokemonDetail.data.models

import com.ailtontech.pokedewai.data.models.NamedAPIResourceDto
import com.ailtontech.pokedewai.features.home.data.models.TypeDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PastTypesDto(
    @SerialName("generation")
    val generation: NamedAPIResourceDto,
    @SerialName("types")
    val types: List<TypeDto>
)
