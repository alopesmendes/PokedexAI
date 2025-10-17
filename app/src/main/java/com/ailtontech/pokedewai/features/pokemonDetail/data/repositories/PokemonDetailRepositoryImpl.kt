package com.ailtontech.pokedewai.features.pokemonDetail.data.repositories

import com.ailtontech.pokedewai.data.errors.Error
import com.ailtontech.pokedewai.features.pokemonDetail.data.datasources.remote.IPokemonDetailRemoteDatasource
import com.ailtontech.pokedewai.features.pokemonDetail.data.mappers.toEntity
import com.ailtontech.pokedewai.features.pokemonDetail.data.mappers.toPokemonDetailFailure
import com.ailtontech.pokedewai.features.pokemonDetail.domain.entities.PokemonDetail
import com.ailtontech.pokedewai.features.pokemonDetail.domain.repositories.IPokemonDetailRepository

class PokemonDetailRepositoryImpl(
    private val remoteDatasource: IPokemonDetailRemoteDatasource
) : IPokemonDetailRepository {
    override suspend fun getPokemonDetail(name: String): Result<PokemonDetail> {
        return try {
            val pokemonDetailDto = remoteDatasource.getPokemonDetail(name)
            Result.success(pokemonDetailDto.toEntity())
        } catch (e: Error) {
            Result.failure(e.toPokemonDetailFailure())
        }
    }
}
