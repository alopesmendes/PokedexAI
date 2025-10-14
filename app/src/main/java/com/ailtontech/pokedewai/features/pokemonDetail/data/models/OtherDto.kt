package com.ailtontech.pokedewai.features.pokemonDetail.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OtherDto(
    @SerialName("dream_world")
    val dreamWorld: DreamWorldDto,
    @SerialName("home")
    val home: HomeDto,
    @SerialName("official-artwork")
    val officialArtwork: OfficialArtworkDto,
    @SerialName("showdown")
    val showdown: ShowdownDto
)
