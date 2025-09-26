package com.ailtontech.pokedewai.di

import org.koin.core.module.Module

val appModules: List<Module> = listOf(
    datasourceModule,
    repositoryModule,
    viewModelModule,
    useCasesModule,
    reducerModule,
    dispatcherModule,
)
