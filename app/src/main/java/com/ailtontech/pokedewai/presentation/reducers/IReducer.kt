package com.ailtontech.pokedewai.presentation.reducers

interface IReducer<State : IReducer.IState, Event : IReducer.IEvent, Effect : IReducer.IEffect> {
    suspend operator fun invoke(state: State, event: Event): Next<State, Effect>

    interface IEvent

    interface IEffect

    interface IState
}

data class Next<State : IReducer.IState, Effect : IReducer.IEffect>(
    val state: State,
    val effect: Effect? = null
)
