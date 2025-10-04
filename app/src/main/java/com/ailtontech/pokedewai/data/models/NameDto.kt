package com.ailtontech.pokedewai.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NameDto(
    @SerialName("name")
    val name: String,
    @SerialName("language")
    val language: NamedAPIResourceDto,
)
