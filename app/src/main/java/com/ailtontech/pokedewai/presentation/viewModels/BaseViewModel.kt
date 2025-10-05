package com.ailtontech.pokedewai.presentation.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ailtontech.pokedewai.presentation.reducers.IReducer
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<State : IReducer.IState, Event : IReducer.IEvent, Effect : IReducer.IEffect>(
    initialState: State,
    private val reducer: IReducer<State, Event, Effect>
) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = initialState
    )

    private val _effects = Channel<Effect>(capacity = Channel.CONFLATED)
    val effect = _effects
        .receiveAsFlow()
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            replay = 1
        )

    fun sendEffect(effect: Effect) {
        viewModelScope.launch {
            _effects.send(effect)
        }
    }

    fun sendEvent(event: Event) {
        viewModelScope.launch {
            val (newState, effect) = reducer(_state.value, event)

            _state.update { newState }

            if (effect != null) {
                sendEffect(effect)
            }

        }
    }
}