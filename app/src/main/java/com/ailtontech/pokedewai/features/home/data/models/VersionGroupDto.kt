package com.ailtontech.pokedewai.features.home.data.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VersionGroupDto(
    @SerialName("name")
    val name: String?,
    @SerialName("url")
    val url: String?
)