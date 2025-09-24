package com.ailtontech.pokedewai

import android.app.Application
import com.ailtontech.pokedewai.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PokedexApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger() // Use androidLogger for Koin logging
            androidContext(this@PokedexApplication) // Provide Android context
            modules(appModules) // Declare your Koin modules
        }
    }
}
