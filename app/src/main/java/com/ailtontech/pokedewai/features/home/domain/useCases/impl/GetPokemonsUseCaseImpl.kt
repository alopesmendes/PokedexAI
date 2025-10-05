package com.ailtontech.pokedewai.features.home.domain.useCases.impl

import com.ailtontech.pokedewai.features.home.domain.commands.PokemonListQuery
import com.ailtontech.pokedewai.features.home.domain.commands.PokemonPaginationParams
import com.ailtontech.pokedewai.features.home.domain.repositories.IPokemonsRepository
import com.ailtontech.pokedewai.features.home.domain.useCases.IGetPokemonsUseCase
import com.ailtontech.pokedewai.features.home.failures.PokemonsFailure

class GetPokemonsUseCaseImpl(
    private val repository: IPokemonsRepository
): IGetPokemonsUseCase {
    override suspend fun invoke(command: PokemonPaginationParams): PokemonListQuery {
        val result = repository.getPaginatedPokemons(
            count = command.count,
            offset = command.offset,
            limit = command.limit
        )

        return result.fold(
            onSuccess = { value -> PokemonListQuery.Success(value) },
            onFailure = { error -> PokemonListQuery.Error(error as PokemonsFailure) },
        )
    }
}
