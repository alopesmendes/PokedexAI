package com.ailtontech.pokedewai.features.pokemonDetail.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenerationIIDto(
    @SerialName("crystal")
    val crystal: CrystalDto,
    @SerialName("gold")
    val gold: GoldDto,
    @SerialName("silver")
    val silver: SilverDto
)
