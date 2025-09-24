package com.ailtontech.pokedewai.presentation.reducers

interface Reducer<State, Event, Effect> {
    operator fun invoke(state: State, event: Event): Next<State, Effect>
}

data class Next<State, Effect>(
    val state: State,
    val effect: Effect? = null
)
