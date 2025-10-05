package com.ailtontech.pokedewai.features.home.data.repositories

import com.ailtontech.pokedewai.data.errors.Error
import com.ailtontech.pokedewai.features.home.data.datasources.remote.IPokemonFormRemoteDatasource
import com.ailtontech.pokedewai.features.home.data.datasources.remote.IPokemonListRemoteDatasource
import com.ailtontech.pokedewai.features.home.data.mappers.toEntity
import com.ailtontech.pokedewai.features.home.data.mappers.toPokemonsFailure
import com.ailtontech.pokedewai.features.home.domain.entities.PokemonList
import com.ailtontech.pokedewai.features.home.domain.repositories.IPokemonsRepository
import com.ailtontech.pokedewai.features.home.failures.PokemonsFailure
import com.ailtontech.pokedewai.utils.Tools
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class PokemonsRepositoryImpl(
    private val pokemonListRemoteDatasource: IPokemonListRemoteDatasource,
    private val pokemonFormRemoteDatasource: IPokemonFormRemoteDatasource,
) : IPokemonsRepository {
    override suspend fun getPaginatedPokemons(
        count: Int?,
        offset: Int,
        limit: Int
    ): Result<PokemonList> {
        when {
            count != null && count < 0 -> return Result.failure(PokemonsFailure.CountIsNegativeFailure())
            offset < 0 -> return Result.failure(PokemonsFailure.OffsetIsNegativeFailure())
            limit <= 0 -> return Result.failure(PokemonsFailure.LimitIsZeroOrNegativeFailure())
            count != null && offset + limit > count -> return Result.failure(PokemonsFailure.OffsetGoesOverTotalFailure())
        }

        return try {
            val pokemonListDto = pokemonListRemoteDatasource.getPokemonList(
                offset = offset,
                limit = limit,
            )

            val pokemonFormsById = coroutineScope {
                pokemonListDto.results
                    .map { result ->
                        async {
                            pokemonFormRemoteDatasource.getPokemonForm(result.name)
                        }
                    }
                    .awaitAll()
                    .map { pokemonFormDto -> pokemonFormDto.toEntity() }
                    .associateBy { pokemonForm -> pokemonForm.id }
            }

            Result.success(pokemonListDto.toEntity { id -> pokemonFormsById[id] })
        } catch (e: Error) {
            Result.failure(e.toPokemonsFailure())
        }
    }

}