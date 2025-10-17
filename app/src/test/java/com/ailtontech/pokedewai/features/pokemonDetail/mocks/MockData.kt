package com.ailtontech.pokedewai.features.pokemonDetail.mocks

import com.ailtontech.pokedewai.data.models.NamedAPIResourceDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.AbilitiesDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.AnimatedDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.BlackWhiteDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.CriesDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.CrystalDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.DiamondPearlDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.DreamWorldDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.EmeraldDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.FireredLeafgreenDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.GameIndiceDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.GenerationIDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.GenerationIIDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.GenerationIIIDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.GenerationIVDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.GenerationVDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.GenerationVIDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.GenerationVIIDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.GenerationVIIIDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.GenerationVersionsDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.GoldDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.HeartgoldSoulsilverDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.HeldItemDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.HomeDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.IconsDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.MoveDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.OfficialArtworkDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.OmegarubyAlphasapphireDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.OtherDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.PastAbilitiesDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.PastTypesDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.PlatinumDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.PokemonDetailDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.RedBlueDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.RubySapphireDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.ShowdownDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.SilverDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.SpritesDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.StatDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.TypeDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.UltraSunUltraMoonDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.XYDto
import com.ailtontech.pokedewai.features.pokemonDetail.data.models.YellowDto
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

internal fun mockPokemonDetailDto(
    id: Int = 1,
    name: String = "bulbasaur",
    height: Int = 7,
    weight: Int = 69,
    baseExperience: Int = 64,
    order: Int = 1,
    isDefault: Boolean = true,
    locationAreaEncounters: String = "",
    abilities: List<AbilitiesDto> = emptyList(),
    cries: CriesDto = mockCriesDto(),
    forms: List<NamedAPIResourceDto> = emptyList(),
    gameIndices: List<GameIndiceDto> = emptyList(),
    heldItems: List<HeldItemDto> = emptyList(),
    moves: List<MoveDto> = emptyList(),
    pastAbilities: List<PastAbilitiesDto> = emptyList(),
    pastTypes: List<PastTypesDto> = emptyList(),
    species: NamedAPIResourceDto = NamedAPIResourceDto("bulbasaur", ""),
    sprites: SpritesDto = mockSpritesDto(),
    stats: List<StatDto> = emptyList(),
    types: List<TypeDto> = emptyList()
) = PokemonDetailDto(
    id = id,
    name = name,
    height = height,
    weight = weight,
    baseExperience = baseExperience,
    order = order,
    isDefault = isDefault,
    locationAreaEncounters = locationAreaEncounters,
    abilities = abilities,
    cries = cries,
    forms = forms,
    gameIndices = gameIndices,
    heldItems = heldItems,
    moves = moves,
    pastAbilities = pastAbilities,
    pastTypes = pastTypes,
    species = species,
    sprites = sprites,
    stats = stats,
    types = types
)

internal fun mockPokemonDetail(
    id: Int = 1,
    name: String = "bulbasaur",
    weight: Int = 69,
    order: Int = 1,
    isDefault: Boolean = true,
    abilities: List<Abilities> = emptyList(),
    species: String = "bulbasaur",
    stats: List<Stat> = emptyList(),
    types: List<Type> = emptyList(),
    moves: List<Move> = emptyList(),
    sprites: Sprites = mockSprites(),
) = PokemonDetail(
    id = id,
    isDefault = isDefault,
    order = order,
    name = name,
    weight = weight,
    abilities = abilities,
    species = species,
    stats = stats,
    types = types,
    moves = moves,
    sprites = sprites,
)

internal fun mockCriesDto() = CriesDto(latest = "", legacy = "")

internal fun mockSpritesDto() = SpritesDto(
    backDefault = "",
    backFemale = "",
    backShiny = "",
    backShinyFemale = "",
    frontDefault = "",
    frontFemale = "",
    frontShiny = "",
    frontShinyFemale = "",
    other = mockOtherDto(),
    versions = mockGenerationVersionsDto()
)

internal fun mockOtherDto() = OtherDto(
    dreamWorld = DreamWorldDto("", ""),
    home = HomeDto("", "", "", ""),
    officialArtwork = OfficialArtworkDto("", ""),
    showdown = ShowdownDto("", "", "", "", "", "", "", "")
)

internal fun mockGenerationVersionsDto() = GenerationVersionsDto(
    generationI = GenerationIDto(
        RedBlueDto("", "", "", "", "", ""),
        YellowDto("", "", "", "", "", "")
    ),
    generationIi = GenerationIIDto(mockCrystalDto(), mockGoldDto(), mockSilverDto()),
    generationIii = GenerationIIIDto(
        EmeraldDto("", ""),
        FireredLeafgreenDto("", "", "", ""),
        RubySapphireDto("", "", "", "")
    ),
    generationIv = GenerationIVDto(
        DiamondPearlDto("", "", "", "", "", "", "", ""),
        HeartgoldSoulsilverDto("", "", "", "", "", "", "", ""),
        PlatinumDto("", "", "", "", "", "", "", "")
    ),
    generationV = GenerationVDto(mockBlackWhiteDto()),
    generationVi = GenerationVIDto(
        OmegarubyAlphasapphireDto("", "", "", ""),
        XYDto("", "", "", "")
    ),
    generationVii = GenerationVIIDto(IconsDto("", ""), UltraSunUltraMoonDto("", "", "", "")),
    generationViii = GenerationVIIIDto(IconsDto("", ""))
)

internal fun mockCrystalDto() = CrystalDto("", "", "", "", "", "", "", "")
internal fun mockGoldDto() = GoldDto("", "", "", "", "")
internal fun mockSilverDto() = SilverDto("", "", "", "", "")
internal fun mockBlackWhiteDto() =
    BlackWhiteDto(AnimatedDto("", "", "", "", "", "", "", ""), "", "", "", "", "", "", "", "")

internal fun mockSprites() = Sprites(
    backDefault = "",
    backFemale = "",
    backShiny = "",
    backShinyFemale = "",
    frontDefault = "",
    frontFemale = "",
    frontShiny = "",
    frontShinyFemale = "",
    other = mockOther(),
)

internal fun mockOther() = Other(
    home = Home("", "", "", ""),
    officialArtwork = OfficialArtwork("", ""),
    showdown = Showdown("", "", "", "", "", "", "", "")
)
