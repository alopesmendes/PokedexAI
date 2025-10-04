package com.ailtontech.pokedewai.features.home.data.models


import com.ailtontech.pokedewai.data.models.NameDto
import com.ailtontech.pokedewai.data.models.NamedAPIResourceDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonFormDto(
    @SerialName("form_name")
    val formName: String?,
    @SerialName("form_names")
    val formNames: List<NameDto>,
    @SerialName("form_order")
    val formOrder: Int,
    @SerialName("id")
    val id: Int,
    @SerialName("is_battle_only")
    val isBattleOnly: Boolean,
    @SerialName("is_default")
    val isDefault: Boolean,
    @SerialName("is_mega")
    val isMega: Boolean,
    @SerialName("name")
    val name: String,
    @SerialName("names")
    val names: List<NameDto>,
    @SerialName("order")
    val order: Int,
    @SerialName("pokemon")
    val pokemon: NamedAPIResourceDto,
    @SerialName("sprites")
    val sprites: PokemonFormSpritesDto,
    @SerialName("types")
    val types: List<TypeDto>,
    @SerialName("version_group")
    val versionGroup: NamedAPIResourceDto
)