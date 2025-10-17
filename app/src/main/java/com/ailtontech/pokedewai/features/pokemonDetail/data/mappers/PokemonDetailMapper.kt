package com.ailtontech.pokedewai.features.pokemonDetail.data.mappers

import com.ailtontech.pokedewai.features.pokemonDetail.data.models.AbilitiesDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.HomeDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.MoveDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.OfficialArtworkDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.OtherDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.PokemonDetailDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.ShowdownDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.SpritesDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.StatDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.TypeDto
import com.ailtontech.pokedewai.features.pokemonDetail.domain.entities.Abilities
import com.ailtontech.pokedewai.features.pokemonDetail.domain.entities.Home
import com.ailtontech.pokedewai.features.pokemonDetail.domain.entities.Move
import com.ailtontech.pokedewai.features.pokemonDetail.domain.entities.OfficialArtwork
import com.ailtontech.pokedewai.features.pokemonDetail.domain.entities.Other
import com.ailtontech.pokedewai.features.pokemonDetail.domain.entities.PokemonDetail
import com.ailtontech.pokedewai.features.pokemonDetail.domain.entities.Showdown
import com.ailtontech.pokedewai.features.pokemonDetail.domain.entities.Sprites
import com.ailtontech.pokedewai.features.pokemonDetail.domain.entities.Stat
import com.ailtontech.pokedewai.features.pokemonDetail.domain.entities.Type

fun PokemonDetailDto.toEntity(): PokemonDetail {
    return PokemonDetail(
        id = this.id,
        name = this.name,
        weight = this.weight,
        order = this.order,
        isDefault = this.isDefault,
        abilities = this.abilities.map { it.toEntity() },
        species = this.species.name,
        sprites = this.sprites.toEntity(),
        stats = this.stats.map { it.toEntity() },
        types = this.types.map { it.toEntity() },
        moves = this.moves.map { it.toEntity() }
    )
}

fun SpritesDto.toEntity(): Sprites {
    return Sprites(
        backDefault = this.backDefault,
        backFemale = this.backFemale,
        backShiny = this.backShiny,
        backShinyFemale = this.backShinyFemale,
        frontDefault = this.frontDefault,
        frontFemale = this.frontFemale,
        frontShiny = this.frontShiny,
        frontShinyFemale = this.frontShinyFemale,
        other = this.other.toEntity(),
    )
}

fun OtherDto.toEntity(): Other {
    return Other(
        home = this.home.toEntity(),
        officialArtwork = this.officialArtwork.toEntity(),
        showdown = this.showdown.toEntity()
    )
}

fun HomeDto.toEntity(): Home = Home(
    frontDefault = frontDefault,
    frontFemale = frontFemale,
    frontShiny = frontShiny,
    frontShinyFemale = frontShinyFemale
)

fun OfficialArtworkDto.toEntity(): OfficialArtwork =
    OfficialArtwork(frontDefault = frontDefault, frontShiny = frontShiny)

fun ShowdownDto.toEntity(): Showdown = Showdown(
    backDefault = backDefault,
    backFemale = backFemale,
    backShiny = backShiny,
    backShinyFemale = backShinyFemale,
    frontDefault = frontDefault,
    frontFemale = frontFemale,
    frontShiny = frontShiny,
    frontShinyFemale = frontShinyFemale
)

fun AbilitiesDto.toEntity(): Abilities {
    return Abilities(
        ability = this.ability?.name,
        isHidden = this.isHidden,
        slot = this.slot
    )
}

fun MoveDto.toEntity(): Move {
    return Move(
        move = this.move.name,
    )
}

fun StatDto.toEntity(): Stat {
    return Stat(
        baseStat = this.baseStat,
        effort = this.effort,
        stat = this.stat.name
    )
}

fun TypeDto.toEntity(): Type {
    return Type(
        slot = this.slot,
        type = this.type.name
    )
}
