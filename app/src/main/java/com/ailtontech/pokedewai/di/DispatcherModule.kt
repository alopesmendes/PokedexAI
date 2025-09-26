package com.ailtontech.pokedewai.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

// Qualifiers to distinguish between different CoroutineDispatchers
object DispatcherQualifiers {
    val Io = named("IODispatcher")
    val Default = named("DefaultDispatcher")
    val Main = named("MainDispatcher")
}

/**
 * Koin module for providing CoroutineDispatchers.
 */
val dispatcherModule = module {
    single<CoroutineDispatcher>(DispatcherQualifiers.Io) { Dispatchers.IO }
    single<CoroutineDispatcher>(DispatcherQualifiers.Default) { Dispatchers.Default }
    single<CoroutineDispatcher>(DispatcherQualifiers.Main) { Dispatchers.Main }
}
