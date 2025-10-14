package com.ailtontech.pokedewai.features.pokemonDetail.data.models

import com.ailtontech.pokedewai.data.models.NamedAPIResourceDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonDetailDto(
    @SerialName("abilities")
    val abilities: List<AbilitiesDto>,
    @SerialName("base_experience")
    val baseExperience: Int,
    @SerialName("cries")
    val cries: CriesDto,
    @SerialName("forms")
    val forms: List<NamedAPIResourceDto>,
    @SerialName("game_indices")
    val gameIndices: List<GameIndiceDto>,
    @SerialName("height")
    val height: Int,
    @SerialName("held_items")
    val heldItems: List<HeldItemDto>,
    @SerialName("id")
    val id: Int,
    @SerialName("is_default")
    val isDefault: Boolean,
    @SerialName("location_area_encounters")
    val locationAreaEncounters: String,
    @SerialName("moves")
    val moves: List<MoveDto>,
    @SerialName("name")
    val name: String,
    @SerialName("order")
    val order: Int,
    @SerialName("past_abilities")
    val pastAbilities: List<PastAbilitiesDto>,
    @SerialName("past_types")
    val pastTypes: List<PastTypesDto>,
    @SerialName("species")
    val species: NamedAPIResourceDto,
    @SerialName("sprites")
    val sprites: SpritesDto,
    @SerialName("stats")
    val stats: List<StatDto>,
    @SerialName("types")
    val types: List<TypeDto>,
    @SerialName("weight")
    val weight: Int
)
