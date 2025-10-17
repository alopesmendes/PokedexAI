package com.ailtontech.pokedewai.features.pokemonDetail.domain.usecases

import com.ailtontech.pokedewai.features.pokemonDetail.domain.commands.PokemonDetailParams
import com.ailtontech.pokedewai.features.pokemonDetail.domain.commands.PokemonDetailQuery

interface IGetPokemonDetailUseCase {
    suspend operator fun invoke(params: PokemonDetailParams): PokemonDetailQuery
}
