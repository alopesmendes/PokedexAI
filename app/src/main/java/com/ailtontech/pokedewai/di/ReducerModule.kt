package com.ailtontech.pokedewai.di

import com.ailtontech.pokedewai.features.home.presentation.reducers.PokemonsReducer
import com.ailtontech.pokedewai.presentation.reducers.IReducer
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val reducerModule: Module = module {
    singleOf(::PokemonsReducer) bind IReducer::class
}
