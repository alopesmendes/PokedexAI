package com.ailtontech.pokedewai.features.pokemonDetail.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenerationIVDto(
    @SerialName("diamond-pearl")
    val diamondPearl: DiamondPearlDto,
    @SerialName("heartgold-soulsilver")
    val heartgoldSoulsilver: HeartgoldSoulsilverDto,
    @SerialName("platinum")
    val platinum: PlatinumDto
)
