package com.ailtontech.pokedewai.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NamedAPIResourceDto(
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String
)
