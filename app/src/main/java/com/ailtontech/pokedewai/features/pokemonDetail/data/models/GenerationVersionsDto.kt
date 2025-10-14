package com.ailtontech.pokedewai.features.pokemonDetail.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenerationVersionsDto(
    @SerialName("generation-i")
    val generationI: GenerationIDto,
    @SerialName("generation-ii")
    val generationIi: GenerationIIDto,
    @SerialName("generation-iii")
    val generationIii: GenerationIIIDto,
    @SerialName("generation-iv")
    val generationIv: GenerationIVDto,
    @SerialName("generation-v")
    val generationV: GenerationVDto,
    @SerialName("generation-vi")
    val generationVi: GenerationVIDto,
    @SerialName("generation-vii")
    val generationVii: GenerationVIIDto,
    @SerialName("generation-viii")
    val generationViii: GenerationVIIIDto
)
