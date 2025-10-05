package com.ailtontech.pokedewai.di

import com.ailtontech.pokedewai.features.home.data.repositories.PokemonsRepositoryImpl
import com.ailtontech.pokedewai.features.home.domain.repositories.IPokemonsRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val repositoryModule: Module = module {
    singleOf(::PokemonsRepositoryImpl) bind IPokemonsRepository::class
}
