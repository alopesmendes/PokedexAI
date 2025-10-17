package com.ailtontech.pokedewai.features.pokemonDetail.domain.usecases

import com.ailtontech.pokedewai.features.pokemonDetail.domain.commands.PokemonDetailParams
import com.ailtontech.pokedewai.features.pokemonDetail.domain.commands.PokemonDetailQuery
import com.ailtontech.pokedewai.features.pokemonDetail.domain.failures.PokemonDetailFailure
import com.ailtontech.pokedewai.features.pokemonDetail.domain.repositories.IPokemonDetailRepository

class GetPokemonDetailUseCaseImpl(
    private val repository: IPokemonDetailRepository
) : IGetPokemonDetailUseCase {
    override suspend operator fun invoke(params: PokemonDetailParams): PokemonDetailQuery {
        return repository.getPokemonDetail(params.name).fold(
            onSuccess = { PokemonDetailQuery.Success(it) },
            onFailure = { PokemonDetailQuery.Failure(it as PokemonDetailFailure) }
        )
    }
}
