package com.ailtontech.pokedewai.di

import com.ailtontech.pokedewai.features.home.domain.useCases.IGetPokemonsUseCase
import com.ailtontech.pokedewai.features.home.domain.useCases.impl.GetPokemonsUseCaseImpl
import com.ailtontech.pokedewai.features.pokemonDetail.domain.usecases.GetPokemonDetailUseCaseImpl
import com.ailtontech.pokedewai.features.pokemonDetail.domain.usecases.IGetPokemonDetailUseCase
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val useCasesModule: Module = module {
    singleOf(::GetPokemonsUseCaseImpl) bind IGetPokemonsUseCase::class
    singleOf(::GetPokemonDetailUseCaseImpl) bind IGetPokemonDetailUseCase::class
}
