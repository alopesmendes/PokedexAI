package com.ailtontech.pokedewai.test_utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * A base class for ViewModel unit tests that handles the main coroutine dispatcher
 * and provides a [TestDispatcher] for managing coroutines.
 */
@ExperimentalCoroutinesApi
abstract class BaseViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
}

/**
 * A JUnit rule that sets the main coroutine dispatcher to a [TestDispatcher]
 * for the duration of the test.
 */
@ExperimentalCoroutinesApi
class MainDispatcherRule(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}
