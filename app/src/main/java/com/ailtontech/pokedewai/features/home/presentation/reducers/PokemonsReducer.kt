package com.ailtontech.pokedewai.features.home.presentation.reducers

import com.ailtontech.pokedewai.features.home.domain.commands.PokemonListQuery
import com.ailtontech.pokedewai.features.home.domain.commands.PokemonPaginationParams
import com.ailtontech.pokedewai.features.home.domain.useCases.IGetPokemonsUseCase
import com.ailtontech.pokedewai.features.home.presentation.reducers.effects.PokemonsEffect
import com.ailtontech.pokedewai.features.home.presentation.reducers.events.PokemonsEvent
import com.ailtontech.pokedewai.presentation.reducers.IReducer
import com.ailtontech.pokedewai.presentation.reducers.Next

class PokemonsReducer(
    private val getPokemonsUseCase: IGetPokemonsUseCase,
) : IReducer<PokemonsState, PokemonsEvent, PokemonsEffect> {
    override suspend fun invoke(
        state: PokemonsState,
        event: PokemonsEvent
    ): Next<PokemonsState, PokemonsEffect> {
        when (event) {
            is PokemonsEvent.PokemonsListLoading -> {
                return Next(
                    state = state.copy(
                        isLoading = true,
                        failure = null,
                    )
                )
            }

            is PokemonsEvent.GetPokemonsList -> {
                val pokemonPaginationParams = PokemonPaginationParams(
                    offset = event.offset,
                    limit = event.limit,
                    count = state.count,
                )
                when (val result = getPokemonsUseCase(pokemonPaginationParams)) {
                    is PokemonListQuery.Success -> {
                        return Next(
                            state = state.copy(
                                isLoading = false,
                                limit = result.pokemonList.limit,
                                offset = result.pokemonList.offset,
                                count = result.pokemonList.count,
                                pokemons = state.pokemons + result.pokemonList.pokemonForms,
                                failure = null,
                            )
                        )
                    }

                    is PokemonListQuery.Error -> {
                        return Next(
                            state = state.copy(
                                failure = result.failure,
                                isLoading = false,
                            )
                        )
                    }
                }
            }

            is PokemonsEvent.GetPokemonDetail -> {
                return Next(
                    state = state,
                    effect = PokemonsEffect.NavigateToPokemonDetail(
                        id = event.id
                    )
                )
            }
        }
    }
}