package com.ailtontech.pokedewai.features.pokemonDetail.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RedBlueDto(
    @SerialName("back_default")
    val backDefault: String?,
    @SerialName("back_gray")
    val backGray: String?,
    @SerialName("back_transparent")
    val backTransparent: String?,
    @SerialName("front_default")
    val frontDefault: String?,
    @SerialName("front_gray")
    val frontGray: String?,
    @SerialName("front_transparent")
    val frontTransparent: String?
)
