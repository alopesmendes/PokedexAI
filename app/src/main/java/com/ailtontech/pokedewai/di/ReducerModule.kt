package com.ailtontech.pokedewai.di

import com.ailtontech.pokedewai.features.home.presentation.reducers.PokemonsReducer
import com.ailtontech.pokedewai.features.pokemonDetail.presentation.reducers.PokemonDetailReducer
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val reducerModule: Module = module {
    singleOf(::PokemonsReducer)
    singleOf(::PokemonDetailReducer)
}
