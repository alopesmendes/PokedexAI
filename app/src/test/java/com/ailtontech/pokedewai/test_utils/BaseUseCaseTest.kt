package com.ailtontech.pokedewai.test_utils

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before

/**
 * An abstract base class for use case unit tests.
 *
 * Provides a [TestDispatcher] for managing coroutines in a test environment.
 */
@ExperimentalCoroutinesApi
abstract class BaseUseCaseTest {

    protected lateinit var testDispatcher: TestDispatcher

    @Before
    open fun setUp() {
        testDispatcher = UnconfinedTestDispatcher()
    }
}
