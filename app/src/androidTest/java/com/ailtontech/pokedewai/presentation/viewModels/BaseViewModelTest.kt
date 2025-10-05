package com.ailtontech.pokedewai.presentation.viewModels

import app.cash.turbine.test
import com.ailtontech.pokedewai.presentation.reducers.IReducer
import com.ailtontech.pokedewai.presentation.reducers.Next
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import com.ailtontech.pokedewai.test_utils.BaseViewModelTest as AbstractBaseViewModelTest

// 1. Define simple State, Event, and Effect for testing BaseViewModel
private data class TestState(val data: String = "") : IReducer.IState
private sealed interface TestEvent : IReducer.IEvent {
    data class UpdateState(val newData: String) : TestEvent
    data class SendEffect(val effectData: String) : TestEvent
}

private sealed interface TestEffect : IReducer.IEffect {
    data class ShowToast(val message: String) : TestEffect
}

// 2. Create a simple Reducer for the test
private class TestReducer : IReducer<TestState, TestEvent, TestEffect> {
    override suspend fun invoke(state: TestState, event: TestEvent): Next<TestState, TestEffect> {
        return when (event) {
            is TestEvent.UpdateState -> Next(state = state.copy(data = event.newData))
            is TestEvent.SendEffect -> Next(
                effect = TestEffect.ShowToast(event.effectData),
                state = state
            )
        }
    }
}

// 3. Create a concrete ViewModel implementation for the test
private class TestViewModel(reducer: TestReducer) : BaseViewModel<TestState, TestEvent, TestEffect>(
    initialState = TestState(),
    reducer = reducer
)

@ExperimentalCoroutinesApi
class BaseViewModelTest : AbstractBaseViewModelTest() {

    @Test
    fun givenInitialStateWhenViewModelIsCreatedThenEmitsInitialState() = runTest {
        // Given
        val reducer = TestReducer()

        // When
        val viewModel = TestViewModel(reducer)

        // Then
        viewModel.state.test {
            assertEquals(TestState(), awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun givenUpdateStateEventWhenSendEventIsCalledThenStateIsUpdated() = runTest {
        // Given
        val reducer = TestReducer()
        val viewModel = TestViewModel(reducer)
        val event = TestEvent.UpdateState("new data")

        // When
        viewModel.sendEvent(event)

        // Then
        viewModel.state.test {
            assertEquals(TestState("new data"), awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun givenSendEffectEventWhenSendEventIsCalledThenEffectIsEmitted() = runTest {
        // Given
        val reducer = TestReducer()
        val viewModel = TestViewModel(reducer)
        val event = TestEvent.SendEffect("show toast")

        // When & Then
        viewModel.effect.test {
            viewModel.sendEvent(event)
            assertEquals(TestEffect.ShowToast("show toast"), awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

}
