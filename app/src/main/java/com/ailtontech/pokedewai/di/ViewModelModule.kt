package com.ailtontech.pokedewai.di

import com.ailtontech.pokedewai.features.home.presentation.viewModels.PokemonsViewModel
import com.ailtontech.pokedewai.features.pokemonDetail.presentation.viewModels.PokemonDetailViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule: Module = module {
    // Add your viewModel dependencies here
    viewModelOf(::PokemonsViewModel)
    viewModelOf(::PokemonDetailViewModel)
}
