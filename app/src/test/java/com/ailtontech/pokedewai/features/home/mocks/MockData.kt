package com.ailtontech.pokedewai.features.home.mocks

import com.ailtontech.pokedewai.data.models.NameDto
import com.ailtontech.pokedewai.data.models.NamedAPIResourceDto
import com.ailtontech.pokedewai.features.home.data.models.PokemonFormDto
import com.ailtontech.pokedewai.features.home.data.models.PokemonFormSpritesDto
import com.ailtontech.pokedewai.features.home.data.models.PokemonListDto
import com.ailtontech.pokedewai.features.home.data.models.TypeDto
import com.ailtontech.pokedewai.features.home.domain.entities.PokemonForm
import com.ailtontech.pokedewai.features.home.domain.entities.PokemonList

/**
 * Creates a mock [PokemonFormDto] for testing purposes.
 *
 * @return A mock [PokemonFormDto] instance.
 */
internal fun mockPokemonFormDto(
    id: Int = 1,
    name: String = "bulbasaur",
    order: Int = 1,
    sprites: PokemonFormSpritesDto = mockPokemonFormSpritesDto(),
    types: List<TypeDto> = emptyList(),
    isDefault: Boolean = true,
    isBattleOnly: Boolean = false,
    isMega: Boolean = false,
    formName: String? = null,
    formNames: List<NameDto> = emptyList(),
    formOrder: Int = 1,
    names: List<NameDto> = emptyList(),
    pokemon: NamedAPIResourceDto = NamedAPIResourceDto(name = "bulbasaur", url = "https://pokeapi.co/api/v2/pokemon/1/"),
    versionGroup: NamedAPIResourceDto = NamedAPIResourceDto(name = "red-blue", url = "https://pokeapi.co/api/v2/version-group/1/")
): PokemonFormDto = PokemonFormDto(
    id = id,
    name = name,
    order = order,
    sprites = sprites,
    types = types,
    isDefault = isDefault,
    isBattleOnly = isBattleOnly,
    isMega = isMega,
    formName = formName,
    formNames = formNames,
    formOrder = formOrder,
    names = names,
    pokemon = pokemon,
    versionGroup = versionGroup
)

/**
 * Creates a mock [PokemonFormSpritesDto] for testing purposes.
 *
 * @return A mock [PokemonFormSpritesDto] instance.
 */
internal fun mockPokemonFormSpritesDto(
    frontDefault: String? = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
    frontShiny: String? = null,
    frontFemale: String? = null,
    frontShinyFemale: String? = null,
    backDefault: String? = null,
    backShiny: String? = null,
    backFemale: String? = null,
    backShinyFemale: String? = null
): PokemonFormSpritesDto = PokemonFormSpritesDto(
    frontDefault = frontDefault,
    frontShiny = frontShiny,
    frontFemale = frontFemale,
    frontShinyFemale = frontShinyFemale,
    backDefault = backDefault,
    backShiny = backShiny,
    backFemale = backFemale,
    backShinyFemale = backShinyFemale,
)

/**
 * Creates a mock [PokemonForm] entity for testing purposes.
 *
 * @return A mock [PokemonForm] instance.
 */
internal fun mockPokemonForm(
    id: Int = 1,
    name: String = "bulbasaur",
    order: Int = 1,
    imageUrl: String? = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png",
    types: List<String> = emptyList(),
    isDefault: Boolean = true,
    isBattleOnly: Boolean = false,
    isMega: Boolean = false
): PokemonForm = PokemonForm(
    id = id,
    name = name,
    order = order,
    imageUrl = imageUrl,
    types = types,
    isDefault = isDefault,
    isBattleOnly = isBattleOnly,
    isMega = isMega
)

/**
 * Creates a mock [PokemonListDto] for testing purposes.
 *
 * @return A mock [PokemonListDto] instance.
 */
internal fun mockPokemonListDto(
    count: Int = 100,
    next: String? = "https://pokeapi.co/api/v2/pokemon?offset=0&limit=20",
    previous: String? = null,
    results: List<NamedAPIResourceDto> = listOf(NamedAPIResourceDto("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/"))
): PokemonListDto = PokemonListDto(
    count = count,
    next = next,
    previous = previous,
    results = results
)

/**
 * Creates a mock [PokemonList] entity for testing purposes.
 *
 * @return A mock [PokemonList] instance.
 */
internal fun mockPokemonList(
    count: Int = 100,
    offset: Int? = 0,
    limit: Int? = 20,
    pokemonForms: List<PokemonForm> = listOf(mockPokemonForm())
): PokemonList = PokemonList(
    count = count,
    offset = offset,
    limit = limit,
    pokemonForms = pokemonForms
)
