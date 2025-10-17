package com.ailtontech.pokedewai.features.pokemonDetail.presentation.reducers

import com.ailtontech.pokedewai.features.pokemonDetail.domain.commands.PokemonDetailParams
import com.ailtontech.pokedewai.features.pokemonDetail.domain.commands.PokemonDetailQuery
import com.ailtontech.pokedewai.features.pokemonDetail.domain.usecases.IGetPokemonDetailUseCase
import com.ailtontech.pokedewai.features.pokemonDetail.presentation.reducers.effects.PokemonDetailEffect
import com.ailtontech.pokedewai.features.pokemonDetail.presentation.reducers.events.PokemonDetailEvent
import com.ailtontech.pokedewai.presentation.reducers.IReducer
import com.ailtontech.pokedewai.presentation.reducers.Next

class PokemonDetailReducer(
    private val getPokemonDetailUseCase: IGetPokemonDetailUseCase,
) : IReducer<PokemonDetailState, PokemonDetailEvent, PokemonDetailEffect> {

    override suspend fun invoke(
        state: PokemonDetailState,
        event: PokemonDetailEvent
    ): Next<PokemonDetailState, PokemonDetailEffect> {
        return when (event) {
            PokemonDetailEvent.Loading -> {
                Next(state = state.copy(isLoading = true, failure = null))
            }

            is PokemonDetailEvent.GetPokemonDetail -> {
                val params = PokemonDetailParams(name = event.name)
                when (val result = getPokemonDetailUseCase(params)) {
                    is PokemonDetailQuery.Success -> {
                        Next(
                            state = state.copy(
                                isLoading = false,
                                pokemon = result.pokemonDetail
                            )
                        )
                    }

                    is PokemonDetailQuery.Failure -> {
                        Next(
                            state = state.copy(
                                isLoading = false,
                                failure = result.failure
                            )
                        )
                    }
                }
            }
        }
    }
}
