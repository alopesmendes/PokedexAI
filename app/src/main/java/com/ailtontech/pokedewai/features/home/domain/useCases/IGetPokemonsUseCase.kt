package com.ailtontech.pokedewai.features.home.domain.useCases

import com.ailtontech.pokedewai.features.home.domain.commands.PokemonListQuery
import com.ailtontech.pokedewai.features.home.domain.commands.PokemonPaginationParams

interface IGetPokemonsUseCase {
    suspend operator fun invoke(command: PokemonPaginationParams): PokemonListQuery
}
